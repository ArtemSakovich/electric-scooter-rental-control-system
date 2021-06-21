import http from "../http.common"
import authHeader from "./auth-header";

class CityService {

    addCity(data) {
        return http.post("/manager/city", data, {headers: authHeader()} );
    }

    getAll() {
        return http.get("/cities")
    }

    getById(id) {
        return http.get(`/user/cities/${id}`, { headers: authHeader() });
    }

    update(data) {
        return http.put("/manager/city", data, { headers: authHeader() });
    }

    delete(id) {
        return http.delete(`/manager/city/${id}`, { headers: authHeader() })
    }
}

export default new CityService();