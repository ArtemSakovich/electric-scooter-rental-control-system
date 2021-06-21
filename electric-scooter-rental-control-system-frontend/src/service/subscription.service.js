import http from "../http.common"
import authHeader from "./auth-header";

class SubscriptionService {

    addSubscription(data) {
        return http.post("/manager/subscription", data, { headers: authHeader() });
    }

    getAll() {
        return http.get("/manager/subscriptions", { headers: authHeader() })
    }

    getById(id) {
        return http.get(`/user/subscriptions/${id}`, { headers: authHeader() })
    }

    update(data) {
        return http.put("/manager/subscription", data, { headers: authHeader() });
    }

    delete(id) {
        return http.delete(`/manager/subscription/${id}`, { headers: authHeader() })
    }

    getAllSubscriptionsByUser(id) {
        return http.get(`/user/user-subscriptions/${id}`, { headers: authHeader() })
    }
}

export default new SubscriptionService();