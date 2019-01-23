<template>
  <course-detail :is-edit="false"/>
</template>

<script>
import CourseDetail from './components/CourseDetail'

export default {
  name: 'CreateForm',
  components: { CourseDetail }
}
</script>

