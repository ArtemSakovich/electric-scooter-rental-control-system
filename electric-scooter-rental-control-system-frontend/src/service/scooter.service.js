import http from "../http.common"
import authHeader from "./auth-header";

class ScooterService {

    addScooter(data) {
        return http.post("/manager/scooter", data, { headers: authHeader() });
    }

    getScooter(id) {
        return http.get(`/user/scooters/${id}`, { headers: authHeader() })
    }

    getAll() {
        return http.get("/manager/scooters", { headers: authHeader() })
    }

    update(data) {
        return http.put("/manager/scooter", data, { headers: authHeader() });
    }

    delete(id) {
        return http.delete(`/manager/scooter/${id}`, { headers: authHeader() })
    }

    getAllAvailableScootersByRentalPointId(id) {
        return http.get(`/user/rental-point/available-scooters/${id}`, { headers: authHeader() })
    }


    getAllByRentalPoint(id) {
        return http.get(`/user/rental-point/scooters/${id}`, { headers: authHeader() })
    }
}

export default new ScooterService();