<template>
  <div id="add-blog">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="博客名称" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="博客内容" prop="content">
        <el-input type="textarea" rows="10" size="200px" v-model="ruleForm.content"></el-input>
      </el-form-item>

      <el-form-item label="博客分类" prop="type">
        <el-checkbox-group v-model="ruleForm.type" @change="log" >
          <el-checkbox label="Vue.js" name="type"></el-checkbox>
          <el-checkbox label="Node.js" name="type"></el-checkbox>
          <el-checkbox label="React.js" name="type"></el-checkbox>
          <el-checkbox label="Angular4.js" name="type"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="博客作者" prop="author">
        <el-select v-model="ruleForm.author" placeholder="请选择博客作者">
          <el-option v-for="item in authors" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">添加博客</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        ruleForm: {
          name: '',
          author: '',
          content: '',
          type: []
        },
        authors: ['Lily', 'Lucy', 'Green'],
        rules: {
          name: [
            { required: true, message: '请输入博客名称', trigger: 'blur' },
            { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
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
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var _list = {}
            _list.name = this.ruleForm.name
            _list.author = this.ruleForm.author
            _list.content = this.ruleForm.content
            _list.types = []

            for(var i = 0; i < this.ruleForm.type.length; i++){
              var type = {}
              type["typename"] = this.ruleForm.type[i]
              _list.types.push(type)
            }

            axios.post("http://localhost:8081/blog/save", _list).then(resp=>{
              if(resp.data == "success") {
                this.$alert('添加成功', '恭喜', {
                  confirmButtonText: '确定',
                  callback: action => {
                    window.location.href = "/show-blog"
                  }
                });
              } else {
                alert("添加失败")
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      log(){
        console.log(this.ruleForm.type);
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>
  #add-blog{
    max-width: 600px;
    margin: 20px auto;
  }
  h2{
    border-bottom: 1px solid lightgray;
    margin-bottom: 20px;
    padding-bottom: 10px;
  }
</style>
