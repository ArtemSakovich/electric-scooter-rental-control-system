import http from "../http.common"
import authHeader from "./auth-header";

class RideService {

    getAll() {
        return http.get("/manager/ride-sessions", { headers: authHeader() })
    }

    orderRide(data) {
        return http.post("/user/order-ride", data, { headers: authHeader() })
    }

    getAllByUser(id) {
        return http.get(`/user/ride-sessions/${id}`, { headers: authHeader() });
    }

    getAllByScooterId(id) {
        return http.get(`/manager/scooter/ride-sessions/${id}`, { headers: authHeader() });
    }
}

export default new RideService();