import http from "../http.common"
import authHeader from "./auth-header";

class ScooterModelService {

    addModel(data) {
        return http.post("/manager/scooter-model", data, { headers: authHeader() });
    }

    getById(id) {
        return http.get(`/user/scooter-models/${id}`, { headers: authHeader() });
    }

    getAll() {
        return http.get("/manager/scooter-models", { headers: authHeader() });
    }

    update(data) {
        return http.put("/manager/scooter-model", data, { headers: authHeader() });
    }

    delete(id) {
        return http.delete(`/manager/scooter-model/${id}`, { headers: authHeader() })
    }
}

export default new ScooterModelService();