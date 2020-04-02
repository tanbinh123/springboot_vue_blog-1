<template>
  <div v-mode="'wide'" style="margin: 10px auto">
    <el-backtop/>
    <el-input
            type="text"
            placeholder="搜索"
            v-model="keywords"
            maxlength="32"
            show-word-limit
            @input="keyChange"
    >
    </el-input>
    <div id="list-blog">
      <el-card class="box-card" v-mode="'wide'" v-for="blog in filterBlog">
        <div slot="header" class="clearfix">
          <span v-rainbow style="float: left"><b>{{blog.name | to-uppercase}}</b></span>
          <el-button style="float: right; padding: 3px 0" type="text" @click.prevent="deleteById(blog)" >删除</el-button>
          <el-button style="float: right; padding: 3px 0" type="text"  @click="editBlog(blog)">编辑</el-button>
          <el-button style="float: right; padding: 3px 0" type="text" @click="showDetails(blog)">详情</el-button>
        </div>
        <div class="text item" style="background: #eee;text-align: left">
          {{blog.content | snippet}}
        </div>
      </el-card>
      <el-dialog title="编辑博客" :visible.sync="dialogFormVisible">
        <el-form :model="blogTmp" :rules="rules" ref="blogTmp" label-width="100px" class="demo-ruleForm">
          <el-form-item label="博客名称" prop="name">
            <el-input v-model="blogTmp.name"></el-input>
          </el-form-item>
          <el-form-item label="博客内容" prop="content">
            <el-input type="textarea" rows="10" size="200px" v-model="blogTmp.content"></el-input>
          </el-form-item>

          <el-form-item label="博客分类" prop="type">
            <el-checkbox-group v-model="blogTmp.type" >
              <el-checkbox label="Vue.js" name="type"></el-checkbox>
              <el-checkbox label="Node.js" name="type"></el-checkbox>
              <el-checkbox label="React.js" name="type"></el-checkbox>
              <el-checkbox label="Angular4.js" name="type"></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="博客作者" prop="author">
            <el-select v-model="blogTmp.author" placeholder="请选择博客作者">
              <el-option v-for="item in authors" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('blogTmp')">确认修改</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-dialog
              :title="blog.name"
              :visible.sync="centerDialogVisible"
              width="60%"
              center>
        <span>作者: {{blog.author}}</span>
        <hr/>
        <article>{{blog.content}}</article>
      </el-dialog>
    </div>

    <div class="block" v-show="keywords == ''">
      <el-pagination
              @current-change="handleCurrentChange"
              layout="prev, pager, next"
              :page-size="pageSize"
              :current-page="currPage"
              :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return  {
        blogs : [],
        blog: {},
        blogTmp: {
            name: '',
            author: '',
            content: '',
            type: []
        },
        keywords: '',
        total: 0,
        currPage: 0,
        pageSize: 5,
        dialogFormVisible: false,
        formLabelWidth: '120px',
        // ruleForm: {
        //   name: '',
        //   author: '',
        //   content: '',
        //   type: []
        // },
        authors: ['Lily', 'Lucy', 'Green'],
        rules: {
          name: [
            { required: true, message: '请输入博客名称', trigger: 'blur' },
            { min: 2, max: 64, message: '长度在 2 到 64 个字符', trigger: 'blur' }
          ],
          author: [
            { required: true, message: '请选择博客作者', trigger: 'change' }
          ],
          type: [
            { type: 'array', required: true, message: '请至少选择一个博客分类', trigger: 'change' }
          ],
          content: [
            { required: true, message: '请填写博客内容', trigger: 'blur' }
          ]
        },
        centerDialogVisible: false
      }
    },
    created() {
      axios.get("http://localhost:8081/blog/findAll/"+this.currPage+"/" + this.pageSize).then(resp => {
        this.blogs = resp.data.content
        this.total = resp.data.totalElements
    }).catch((error) => {
        this.$message({
          type: 'error',
          message: error
        })
      })
    },
    computed:{
      filterBlog: function(){
        return this.blogs.filter(blog => {
          return blog.name.match(this.keywords)
        })
      }
    },
    methods:{
      handleCurrentChange(val) {
        axios.get("http://localhost:8081/blog/findAll/"+(val-1)+"/" + this.pageSize).then(resp => {
          this.blogs = resp.data.content
          this.total = resp.data.totalElements
        })
      },
      keyChange() {
        if(this.keywords == '') {
          axios.get("http://localhost:8081/blog/findAll/"+this.currPage+"/" + this.pageSize).then(resp => {
            this.blogs = resp.data.content
            this.total = resp.data.totalElements
          })
        } else {
          axios.get("http://localhost:8081/blog/findAll/0/" + this.total).then(resp => {
            this.blogs = resp.data.content
            this.total = resp.data.totalElements
            console.log(this.keywords)
          })
        }
      },
      deleteById(blog){
        this.$confirm('此操作将永久删除该博客, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.delete('http://localhost:8081/blog/deleteById/' + blog.id).then(resp => {
            if (resp.status != 200) {
              return this.$message.error('删除博客'+blog.name+'失败')
            } else {
              this.$message({
                type: 'success',
                message: '删除博客《'+blog.name+'》成功'
              })
              setTimeout(function () {
                window.location.reload()

              }, 200)
            }

          }).catch((error) => {
            this.$message({
              type: 'error',
              message: error
            })
          })


        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      editBlog(blog){
        this.dialogFormVisible = true
        this.blog = blog
        var type = []
        for(var i = 0; i < blog.types.length; i++){
          type.push(blog.types[i].typename)
        }
        this.blogTmp.name = this.blog.name
        this.blogTmp.author = this.blog.author
        this.blogTmp.content = this.blog.content
        this.blogTmp.type = type
      },
      showDetails(blog){
        this.centerDialogVisible = true
        this.blog = blog
      },
      submitForm(blog) {
        this.$refs[blog].validate((valid) => {
          if (valid) {
            var typeArr = []
            for(var i = 0; i < this.blogTmp.type.length; i++) {
              var types = {}
              types.typename = this.blogTmp.type[i]
              typeArr.push(types)
            }
            this.blog.name = this.blogTmp.name
            this.blog.author = this.blogTmp.author
            this.blog.content = this.blogTmp.content
            this.blog.types = typeArr
              axios.put("http://localhost:8081/blog/update", this.blog).then(resp => {
              if(resp.data == "success"){
                // this.$message("图书添加成功")
                this.$alert('《' + this.blog.name + '》' + '修改成功', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                      window.location.reload()
                  }
                });
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
    margin: 20px auto;
  }
</style>