<script setup>
import AdminDashboard from './components/admin/dashboard/AdminDashboard.vue'
import CustomerManagement from "./components/admin/customer/CustomerManagement.vue";
import AppFooter from "./components/admin/main-frame-admin/Footer.vue";
import employeeManagement from "@/components/admin/employee/EmployeeManagement.vue";
</script>

<template>
<!--  <AdminDashboard />-->
<!--  <CustomerManagement />-->
<!--  <employeeManagement />-->
  <router-view />
<!--  <AppFooter />-->
</template>
