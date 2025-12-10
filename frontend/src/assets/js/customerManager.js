import { ref, onMounted } from "vue"
import axios from "axios"

export function useCustomer() {

    /* ===========================
       STATE
    ============================ */
    const customers = ref([])

    const customer = ref({
        id: "",
        name: "",
        email: "",
        password: "",
        phone: "",
        role: "ROLE_CUSTOMER" // luôn mặc định
    })

    const isEdit = ref(false)


    /* ===========================
       LOAD ALL CUSTOMERS
    ============================ */
    const loadCustomers = async () => {
        try {
            const res = await axios.get("http://localhost:8080/api/customers")
            customers.value = res.data
        } catch (err) {
            console.error("Load customers error:", err)
        }
    }
    onMounted(loadCustomers)


    /* ===========================
       SAVE CUSTOMER (CREATE / UPDATE)
    ============================ */
    const saveCustomer = async () => {
        try {
            // luôn đặt lại role mặc định
            customer.value.role = "ROLE_CUSTOMER"

            if (!isEdit.value) {
                // CREATE
                await axios.post("http://localhost:8080/api/customers", customer.value)
            } else {
                // UPDATE
                await axios.put(`http://localhost:8080/api/customers/${customer.value.id}`, customer.value)
            }

            alert("Saved successfully!")
            resetForm()
            loadCustomers()

        } catch (err) {
            console.error("Save error:", err)
            alert("Error saving customer!")
        }
    }


    /* ===========================
       EDIT (LOAD 1 CUSTOMER)
    ============================ */
    const editCustomer = (c) => {
        customer.value = { ...c }
        isEdit.value = true
    }


    /* ===========================
       DELETE
    ============================ */
    const deleteCustomer = async (id) => {
        try {
            if (!confirm("Are you sure?")) return

            await axios.delete(`http://localhost:8080/api/customers/${id}`)
            loadCustomers()

        } catch (err) {
            console.error("Delete error:", err)
        }
    }


    /* ===========================
       RESET FORM
    ============================ */
    const resetForm = () => {
        customer.value = {
            id: "",
            name: "",
            email: "",
            password: "",
            phone: "",
            role: "ROLE_CUSTOMER"
        }
        isEdit.value = false
    }


    return {
        customers,
        customer,
        isEdit,
        loadCustomers,
        saveCustomer,
        editCustomer,
        deleteCustomer,
        resetForm
    }
}
