<template>
  <div class="d-flex">
    <Sidebar />

    <div class="main-content flex-grow-1" style="margin-left:260px; padding:20px; background:#f5f6fa; min-height:100vh;">
      <div class="container py-4">

        <h2 class="mb-4 text-primary fw-bold">Customer Management</h2>

        <!-- FORM -->
        <div class="card shadow-sm mb-4">
          <div class="card-body">
            <form @submit.prevent="saveCustomer" class="row g-3">

              <div class="col-md-6">
                <label class="form-label">Customer ID:</label>
                <input type="text" class="form-control" placeholder="VD: KH001"
                       required v-model="customer.id" :disabled="isEdit" />
              </div>

              <div class="col-md-6">
                <label class="form-label">Name:</label>
                <input type="text" class="form-control" placeholder="Customer name"
                       required v-model="customer.name" />
              </div>

              <div class="col-md-6">
                <label class="form-label">Email:</label>
                <input type="email" class="form-control" placeholder="Email"
                       required v-model="customer.email" />
              </div>

              <div class="col-md-6">
                <label class="form-label">Password:</label>
                <input type="password" class="form-control"
                       placeholder="******" required v-model="customer.password" />
              </div>

              <div class="col-md-6">
                <label class="form-label">Phone:</label>
                <input type="text" class="form-control" placeholder="Phone number"
                       v-model="customer.phone" />
              </div>

              <input type="hidden" v-model="customer.role" />

              <div class="col-md-12">
                <button type="submit" class="btn btn-primary px-4">Save</button>
                <button type="button" class="btn btn-secondary ms-2" @click="resetForm">Clear</button>
              </div>

            </form>
          </div>
        </div>

        <!-- TABLE -->
        <div class="card shadow-sm">
          <div class="card-body">
            <table class="table table-bordered table-hover">
              <thead class="table-secondary">
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Role</th>
                <th style="width: 150px;">Actions</th>
              </tr>
              </thead>

              <tbody>
              <tr v-for="c in customers" :key="c.id">
                <td>{{ c.id }}</td>
                <td>{{ c.name }}</td>
                <td>{{ c.email }}</td>
                <td>{{ c.phone }}</td>
                <td>{{ c.role }}</td>
                <td>
                  <button class="btn btn-warning btn-sm" @click="editCustomer(c)">Edit</button>
                  <button class="btn btn-danger btn-sm ms-1" @click="deleteCustomer(c.id)">Delete</button>
                </td>
              </tr>
              </tbody>

            </table>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import Sidebar from "../main-frame-admin/Sidebar.vue";
import { useCustomer } from "@/assets/js/customerManager.js";

const {
  customers,
  customer,
  isEdit,
  loadCustomers,
  saveCustomer,
  editCustomer,
  deleteCustomer,
  resetForm
} = useCustomer();

onMounted(() => {
  loadCustomers();  // <-- QUAN TRỌNG
});
</script>



<style scoped>
.main-content-wrapper {
  background: #fff;
  padding: 20px;
  border-radius: 10px;
}
</style>
