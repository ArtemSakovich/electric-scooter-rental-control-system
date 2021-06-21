import http from "../http.common"
import authHeader from "./auth-header";

class RentalPointService {

    addScooterRentalPoint(data) {
        return http.post("/manager/scooter-rental-point", data, { headers: authHeader() });
    }

    getAllByCity(id) {
        return http.get(`/scooter-rental-points/${id}`);
    }

    getById(id) {
        return http.get(`/user/rental-points/${id}`, { headers: authHeader() });
    }

    getAll() {
        return http.get("/user/rental-points", { headers: authHeader() });
    }

    update(data) {
        return http.put("/manager/scooter-rental-point", data, { headers: authHeader() });
    }

    delete(id) {
        return http.delete(`/manager/scooter-rental-point/${id}`, { headers: authHeader() })
    }
}

export default new RentalPointService();