<template>
  <course-chapter-detail :is-edit="true"/>
</template>

<script>
import CourseChapterDetail from './components/CourseChapterDetail'

export default {
  name: 'EditChapterForm',
  components: { CourseChapterDetail }
}
</script>

