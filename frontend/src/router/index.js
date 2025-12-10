import { createRouter, createWebHistory } from 'vue-router';
import AdminDashboard from "@/components/admin/dashboard/AdminDashboard.vue";

// Employee
import EmployeeManagement from '../components/admin/employee/EmployeeManagement.vue';
import EmployeeForm from '../components/admin/employee/EmployeeForm.vue';
import FormShipper from "@/components/admin/employee/FormShipper.vue";
import CustomerBillList from "@/components/admin/employee/CustomerBillList.vue";

// Customer
import CustomerManagement from "@/components/admin/customer/CustomerManagement.vue";

// Products
import ProductList from "@/components/admin/products/ProductList.vue";
import ProductForm from "@/components/admin/products/ProductForm.vue";
import BrandManagement from "@/components/admin/products/BrandManagement.vue";

const routes = [
    // Dashboard
    {
        path: '/dashboard',
        name: 'AdminDashboard',
        component: AdminDashboard
    },

    // Products
    {
        path: '/admin/products',
        name: 'ProductManagement',
        component: ProductList
    },
    {
        path: '/admin/products/form',
        name: 'ProductCreate',
        component: ProductForm
    },
    {
        path: '/admin/products/form/:id',
        name: 'ProductEdit',
        component: ProductForm,
        props: true
    },
    {
        path: '/admin/brand',
        name: 'BrandManagement',
        component: BrandManagement
    },

    // Bills / Shipper
    {
        path: '/admin/bills/manage',
        name: 'CustomerBillList',
        component: CustomerBillList
    },
    {
        path: '/admin/bills/customer/:id',
        name: 'FormShipper',
        component: FormShipper,
        props: true
    },

    // Customer
    {
        path: '/admin/customer',
        name: 'CustomerManagement',
        component: CustomerManagement
    },

    // Employee
    {
        path: '/admin/employee',
        name: 'EmployeeManagement',
        component: EmployeeManagement
    },
    {
        path: '/admin/employee/form',
        name: 'EmployeeCreate',
        component: EmployeeForm
    },
    {
        path: '/admin/employee/form/:id',
        name: 'EmployeeEdit',
        component: EmployeeForm,
        props: true
    },

    // Fallback
    {
        path: '/:pathMatch(.*)*',
        redirect: '/dashboard'
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
