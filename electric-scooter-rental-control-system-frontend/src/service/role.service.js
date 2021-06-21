import http from "../http.common"
import authHeader from "./auth-header";

class RoleService {

    getAll() {
        return http.get("/admin/roles", { headers: authHeader() })
    }

    getById(id) {
        return http.get(`/admin/roles/${id}`, { headers: authHeader() })
    }
}

export default new RoleService();