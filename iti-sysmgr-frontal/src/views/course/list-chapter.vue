<template>
  <div class="app-container">

    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">

      <el-table-column width="120px" align="center" label="章节">
        <template slot-scope="scope">
          <span>{{ scope.row.chapter }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="课程">
        <template slot-scope="scope">
          <span>{{ scope.row.course }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="期数">
        <template slot-scope="scope">
          <span>{{ scope.row.period }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="视频地址">
        <template slot-scope="scope">
          <span>{{ scope.row.videoUrl }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="视频提取码">
        <template slot-scope="scope">
          <span>{{ scope.row.videoCode }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="源码地址">
        <template slot-scope="scope">
          <span>{{ scope.row.sourcecodeUrl }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="源码提取码">
        <template slot-scope="scope">
          <span>{{ scope.row.sourcecodeCode }}</span>
        </template>
      </el-table-column>

      <!-- <el-table-column width="100px" label="Importance">
        <template slot-scope="scope">
          <svg-icon v-for="n in +scope.row.importance" :key="n" icon-class="star" class="meta-item__icon"/>
        </template>
      </el-table-column> -->

      <!-- <el-table-column class-name="status-col" label="Status" width="110">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column> -->

      <!-- <el-table-column min-width="300px" label="Title">
        <template slot-scope="scope">

          <router-link :to="'/example/edit/'+scope.row.id" class="link-type">
            <span>{{ scope.row.title }}</span>
          </router-link>
        </template>
      </el-table-column> -->

      <el-table-column align="center" label="Actions" width="120">
        <template slot-scope="scope">
          <router-link :to="'/documentation/editchapter/'+scope.row.id">
            <el-button type="primary" size="small" icon="el-icon-edit">Edit</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import { fetchChapterList } from '@/api/course'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'CourseList',
  components: { Pagination },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 2
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchChapterList(this.listQuery).then(response => {
        this.list = response.content.list
        this.total = response.content.total
        this.listLoading = false
      })
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
