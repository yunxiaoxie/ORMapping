<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">

      <!-- <sticky :class-name="'sub-navbar '+postForm.status">
        <CommentDropdown v-model="postForm.comment_disabled" />
        <PlatformDropdown v-model="postForm.platforms" />
        <SourceUrlDropdown v-model="postForm.source_uri" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">发布
        </el-button>
        <el-button v-loading="loading" type="warning" @click="draftForm">草稿</el-button>
      </sticky> -->

      <div class="createPost-main-container">
        <el-row>

          <!-- <Warning /> -->

          <el-col :span="24">
            <!-- <el-form-item style="margin-bottom: 40px;" prop="title">
              <MDinput v-model="postForm.title" :maxlength="100" name="name" required>
                章节标题
              </MDinput>
            </el-form-item> -->

            <div class="postInfo-container">
              <el-row>
                <el-col :span="8">
                  <el-form-item prop="period" label-width="80px" label="课程:" class="postInfo-container-item">
                    <el-select
                      v-model="postForm.courseId"
                      placeholder="请选择">
                      <el-option
                        v-for="item in courseNames"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"/>
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item prop="course" label-width="80px" label="课程章节:" class="postInfo-container-item">
                    <el-input v-model="postForm.chapter" placeholder="请输入内容"/>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item prop="period" label-width="80px" label="视频地址:" class="postInfo-container-item">
                    <el-input v-model.number="postForm.videoUrl" placeholder="请输入网盘地址"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item prop="course" label-width="80px" label="视频提取码:" class="postInfo-container-item">
                    <el-input v-model="postForm.videoCode" placeholder="请输入内容"/>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item prop="course" label-width="80px" label="源码地址:" class="postInfo-container-item">
                    <el-input v-model="postForm.sourcecodeUrl" placeholder="请输入网盘地址"/>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item prop="period" label-width="80px" label="源码提取码:" class="postInfo-container-item">
                    <el-input v-model.number="postForm.sourcecodeCode" placeholder="请输入网盘地址"/>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>

        <div class="postInfo-container">
          <el-row>
            <el-col :span="24">
              <tinymce :height="200" v-model="postForm.videoComm"/>
            </el-col>
          </el-row>
        </div>

        <!-- <el-form-item style="margin-bottom: 40px;" label-width="45px" label="摘要:">
          <el-input :rows="1" v-model="postForm.content_short" type="textarea" class="article-textarea" autosize placeholder="请输入内容"/>
          <span v-show="contentShortLength" class="word-counter">{{ contentShortLength }}字</span>
        </el-form-item> -->
        <el-form-item>
          <el-button style="margin-left: 10px;" type="success" @click="submitForm">发布
          </el-button>
        </el-form-item>

        <!-- <el-form-item prop="content" style="margin-bottom: 30px;">
          <Tinymce ref="editor" :height="400" v-model="postForm.content" />
        </el-form-item> -->

        <!-- <el-form-item prop="image_uri" style="margin-bottom: 30px;">
          <Upload v-model="postForm.image_uri" />
        </el-form-item> -->
      </div>
    </el-form>

  </div>
</template>

<script>
// import { mapGetters } from 'vuex'
// import Tinymce from '@/components/Tinymce'
// import Upload from '@/components/Upload/singleImage3'
// import MDinput from '@/components/MDinput'
// import Sticky from '@/components/Sticky' // 粘性header组件
// import { validateURL } from '@/utils/validate'
import { fetchCourseChapter, fetchNames } from '@/api/course'
import { userSearch } from '@/api/remoteSearch'
import Tinymce from '@/components/Tinymce'
// import Warning from './Warning'
// import { CommentDropdown, PlatformDropdown, SourceUrlDropdown } from './Dropdown'

const defaultForm = {
  courseId: '', // 课程名称
  chapter: '', // 课程章节
  videoUrl: '',
  videoCode: '',
  videoComm: '',
  sourcecodeUrl: '',
  sourcecodeCode: ''
}

export default {
  name: 'ChapterDetail',
  //, MDinput, Upload, Sticky, Warning, CommentDropdown, PlatformDropdown, SourceUrlDropdown
  components: { Tinymce },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.field + '为必传项',
          type: 'error'
        })
        callback(new Error(rule.field + '为必传项'))
      } else {
        callback()
      }
    }
    return {
      postForm: Object.assign({}, defaultForm),
      courseNames: null,
      chapterSelected: null,
      loading: false,
      userListOptions: [],
      rules: {
        course: [{ validator: validateRequire }],
        period: [{ validator: validateRequire }]
      },
      tempRoute: {}
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.content_short.length
    },
    lang() {
      return this.$store.getters.language
    }
  },
  created() {
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    } else {
      this.postForm = Object.assign({}, defaultForm)
    }
    this.fetchNamesList()
    // Why need to make a copy of this.$route here?
    // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
    // https://github.com/PanJiaChen/vue-element-admin/issues/1221
    this.tempRoute = Object.assign({}, this.$route)
  },
  methods: {
    fetchData(id) {
      fetchCourseChapter(id).then(response => {
        this.postForm = response.content
        // Just for test
        this.postForm.title += `   Article Id:${this.postForm.id}`
        this.postForm.content_short += `   Article Id:${this.postForm.id}`

        // Set tagsview title
        this.setTagsViewTitle()
      }).catch(err => {
        console.log(err)
      })
    },
    fetchNamesList() {
      fetchNames().then(response => {
        this.courseNames = response.content
      })
    },
    setTagsViewTitle() {
      const title = this.lang === 'zh' ? '编辑' : 'Edit Article'
      const route = Object.assign({}, this.tempRoute, { title: `${title}-${this.postForm.id}` })
      this.$store.dispatch('updateVisitedView', route)
    },
    submitForm() {
      this.postForm.display_time = parseInt(this.display_time / 1000)
      console.log(this.postForm)
      this.$refs.postForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('CreateCourseChapter', this.postForm).then(res => {
            console.log(res)
          })
          this.$notify({
            title: '成功',
            message: '添加课程章节成功',
            type: 'success',
            duration: 2000
          })
          this.postForm.status = 'published'
          this.loading = false
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    getRemoteUserList(query) {
      userSearch(query).then(response => {
        if (!response.data.items) return
        this.userListOptions = response.data.items.map(v => v.name)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "~@/styles/mixin.scss";
.createPost-container {
  position: relative;
  .createPost-main-container {
    padding: 40px 45px 20px 50px;
    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;
      .postInfo-container-item {
        float: left;
      }
    }
  }
  .word-counter {
    width: 40px;
    position: absolute;
    right: -10px;
    top: 0px;
  }
}
</style>
