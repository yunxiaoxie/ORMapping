<template>
  <course-chapter-detail :is-edit="false"/>
</template>

<script>
import CourseChapterDetail from './components/CourseChapterDetail'

export default {
  name: 'CreateChapterForm',
  components: { CourseChapterDetail }
}
</script>

