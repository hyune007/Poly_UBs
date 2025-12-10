<template>
  <div class="page-container">
    <Sidebar />

    <div class="content">
      <div class="container mt-5 bg-white p-4 rounded shadow">
        <h2 class="fw-bold mb-4">{{ isEdit ? 'Cập nhật nhân viên' : 'Thêm nhân viên mới' }}</h2>

        <form @submit.prevent="saveEmployee">
          <div class="mb-3">
            <label class="form-label">Mã nhân viên</label>
            <input v-model="employee.id" class="form-control" :readonly="isEdit" required />
          </div>

          <div class="mb-3">
            <label class="form-label">Tên nhân viên</label>
            <input v-model="employee.name" class="form-control" required />
          </div>

          <div class="mb-3">
            <label class="form-label">Mật khẩu</label>
            <input type="password" v-model="employee.password" class="form-control" :required="!isEdit" />
          </div>

          <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input v-model="employee.phone" class="form-control" />
          </div>

          <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" v-model="employee.email" class="form-control" />
          </div>

          <div class="mb-3">
            <label class="form-label">Địa chỉ</label>
            <input v-model="employee.address" class="form-control" />
          </div>

          <div class="mb-3">
            <label class="form-label">Vai trò</label>
            <select v-model="employee.role" class="form-select">
              <option value="ROLE_EMPLOYEE">Nhân viên</option>
              <option value="ROLE_ADMIN">Admin</option>
            </select>
          </div>

          <div class="mb-3">
            <label class="form-label">Ngày sinh</label>
            <input type="date" v-model="employee.birth" class="form-control" />
          </div>

          <div class="d-flex justify-content-between">
            <router-link to="/admin/employee" class="btn btn-secondary">← Quay lại</router-link>
            <button class="btn btn-success" type="submit">Lưu nhân viên</button>
          </div>
        </form>

      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Sidebar from "../main-frame-admin/Sidebar.vue";
import { ref } from 'vue';

export default {
  components: { Sidebar },

  data() {
    return {
      employee: {
        id: "",
        name: "",
        password: "",
        phone: "",
        email: "",
        address: "",
        role: "ROLE_EMPLOYEE",
        birth: ""
      }
    };
  },

  computed: {
    isEdit() {
      return !!this.$route.params.id;
    }
  },

  mounted() {
    if (this.isEdit) {
      axios.get(`http://localhost:8080/api/employees/${this.$route.params.id}`)
          .then(res => {
            this.employee = res.data;
          });
    }
  },

  methods: {
    saveEmployee() {
      if (this.isEdit) {
        // Cập nhật
        axios.put(`http://localhost:8080/api/employees/${this.employee.id}`, this.employee)
            .then(() => this.$router.push("/admin/employee"));
      } else {
        // Thêm mới
        axios.post("http://localhost:8080/api/employees", this.employee)
            .then(() => this.$router.push("/admin/employee"));
      }
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
  padding: 20px;
  width: 82%;
}
</style>
