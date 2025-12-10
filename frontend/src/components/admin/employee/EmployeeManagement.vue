<template>
  <div class="page-container">
    <Sidebar />

    <div class="content">
      <!-- Header -->
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold fs-3 text-dark">👨‍💼 Quản lý nhân viên</h2>
        <router-link class="btn btn-primary shadow" to="/admin/employee/form">
          + Thêm nhân viên
        </router-link>
      </div>

      <!-- Tìm kiếm -->
      <div class="d-flex mb-4">
        <input v-model="keyword" class="form-control me-2" placeholder="Tìm theo tên nhân viên...">
        <button class="btn btn-secondary" @click="fetchEmployees">Tìm kiếm</button>
      </div>

      <div class="mb-3">
        <strong>Tổng số nhân viên:</strong>
        <span class="text-primary">{{ totalItems }}</span>
      </div>

      <!-- Bảng -->
      <div class="table-responsive shadow rounded">
        <table class="table table-hover align-middle mb-0 bg-white">
          <thead class="bg-dark text-white">
          <tr>
            <th>Mã</th>
            <th>Tên</th>
            <th>SĐT</th>
            <th>Email</th>
            <th>Vai trò</th>
            <th>Ngày sinh</th>
            <th class="text-center">Hành động</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="e in employees" :key="e.id">
            <td>{{ e.id }}</td>
            <td class="fw-semibold">{{ e.name }}</td>
            <td>{{ e.phone }}</td>
            <td>{{ e.email }}</td>
            <td>{{ e.role === 'ROLE_ADMIN' ? 'Quản trị viên' : 'Nhân viên' }}</td>
            <td>{{ formatDate(e.birth) }}</td>

            <td class="text-center">
              <router-link :to="`/admin/employee/form/${e.id}`" class="btn btn-sm btn-warning me-1">✏️</router-link>
              <button class="btn btn-sm btn-danger" @click="deleteEmployee(e.id)">🗑️</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- Phân trang -->
      <nav class="mt-4" v-if="totalPages > 1">
        <ul class="pagination justify-content-center">
          <li class="page-item" :class="{ disabled: currentPage === 0 }">
            <button class="page-link" @click="gotoPage(0)">&laquo;</button>
          </li>

          <li class="page-item" v-for="i in totalPages" :key="i" :class="{ active: i - 1 === currentPage }">
            <button class="page-link" @click="gotoPage(i - 1)">{{ i }}</button>
          </li>

          <li class="page-item" :class="{ disabled: currentPage === totalPages - 1 }">
            <button class="page-link" @click="gotoPage(totalPages - 1)">&raquo;</button>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Sidebar from "../main-frame-admin/Sidebar.vue";

export default {
  components: { Sidebar },

  data() {
    return {
      employees: [],
      keyword: "",
      currentPage: 0,
      totalPages: 1,
      totalItems: 0
    };
  },

  mounted() {
    this.fetchEmployees();
  },

  methods: {
    fetchEmployees(page = 0) {
      const encodedKeyword = encodeURIComponent(this.keyword || "");
      axios
          .get(`http://localhost:8080/api/employees?page=${page}&keyword=${encodedKeyword}`)
          .then(res => {
            this.employees = res.data.content;
            this.totalItems = res.data.totalElements;
            this.totalPages = res.data.totalPages;
            this.currentPage = res.data.number;
          });
    },

    deleteEmployee(id) {
      if (confirm("Xóa nhân viên này?")) {
        axios.delete(`http://localhost:8080/api/employees/${id}`)
            .then(() => this.fetchEmployees(this.currentPage));
      }
    },

    gotoPage(page) {
      this.fetchEmployees(page);
    },

    formatDate(date) {
      if (!date) return "";
      return new Date(date).toLocaleDateString("vi-VN");
    }
  }
};
</script>

<style scoped>
.page-container {
  display: flex;
}

.content {
  margin-left: 260px;
  width: 82%;
  padding: 20px;
}
</style>
