import http from "../http.common"
import authHeader from "./auth-header";

class UserService {

    getAll() {
        return http.get("/manager/users", { headers: authHeader() })
    }

    getById(id) {
        return http.get(`/user/users/${id}`, { headers: authHeader() })
    }

    updateWithoutRole(data) {
        return http.put("/user/user", data, { headers: authHeader() });
    }

    updateWithRole(data) {
        return http.put("/admin/user", data, { headers: authHeader() });
    }

    delete(id) {
        return http.delete(`/admin/user/${id}`, { headers: authHeader() })
    }
}

export default new UserService();