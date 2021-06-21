import React, {Component} from "react";
import {Link, Route, Switch} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./service/auth.service";

import Login from "./component/util/login.component";
import Register from "./component/util/register.component";
import Profile from "./component/util/profile.component";
import CitiesList from "./component/city/city-list.component"
import CitiesManagerList from "./component/city/city-manager-list.component"
import ScooterList from "./component/scooter/scooter-list.component";
import Scooter from "./component/scooter/scooter.component";
import ScooterAdd from "./component/scooter/scooter-add.component";
import RentalPointList from "./component/scooter-rental-point/rental-point-list.component";
import RentalPoint from "./component/scooter-rental-point/scooter-rental-point.component"
import RentalPointAdd from "./component/scooter-rental-point/add-rental-point.component";
import ScooterModelList from "./component/scooter-model/scooter-model-list.component"
import ScooterModelAdd from "./component/scooter-model/scooter-model-add.component"
import ScooterModel from "./component/scooter-model/scooter-model.component"
import CityAdd from "./component/city/city-add.component";
import City from "./component/city/city.component";
import SubscriptionList from "./component/subscription/subscription-list.component";
import SubscriptionAdd from "./component/subscription/add-subscription.component";
import Subscription from "./component/subscription/subscription.component";
import User from "./component/user/user.component";
import UserList from "./component/user/user-list.component";
import UserSubscriptionList from "./component/subscription/user-subscription-list.component";
import OrderRide from "./component/ride/order-ride.component";
import RideSessionsList from "./component/ride/rides-list.component";
import UserRideSessionsList from "./component/ride/user-ride-session.list.component";
import UpdateUserProfile from "./component/user/update-user-profile.component";
import UserScooterList from "./component/scooter/user-scooter-list.component";
import UserRentalPointList from "./component/scooter-rental-point/user-scooter-rental-point.component";
import UserCityList from "./component/city/user-city-list.component";
import RideSessionsForSingleScooter from "./component/ride/ride-session-for-single-scooter.component";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user
      });
    }
  }

  logOut() {
    AuthService.logout();
  }

  render() {
    const { currentUser } = this.state;

    return (
        <div>
          <nav className="navbar navbar-expand navbar-dark bg-dark">
            {!currentUser ? (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/cities"} className="nav-link">
                      View our rental points
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link">
                      Sing in
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/register"} className="nav-link">
                      Sign up
                    </Link>
                  </li>
                </div>
            ) : ("")}
            {currentUser && currentUser.role.includes("ROLE_ADMIN") ? (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/profile"} className="nav-link">
                      {currentUser.email}
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link" onClick={this.logOut}>
                      Log out
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/scooters"} className="nav-link">
                      Scooters
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/rental-points"} className="nav-link">
                      Rental points
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/scooter-models"} className="nav-link">
                      Models
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/cities-manager"} className="nav-link">
                      Cities
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/subscriptions-manager"} className="nav-link">
                      Subscriptions
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/users"} className="nav-link">
                      Users
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/ride-sessions"} className="nav-link">
                      Ride sessions
                    </Link>
                  </li>
                </div>
            ) : ("")}
            {currentUser && currentUser.role.includes("ROLE_MANAGER") ? (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/profile"} className="nav-link">
                      {currentUser.email}
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link" onClick={this.logOut}>
                      Log out
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/scooters"} className="nav-link">
                      Scooters
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/rental-points"} className="nav-link">
                      Rental points
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/scooter-models"} className="nav-link">
                      Models
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/cities-manager"} className="nav-link">
                      Cities
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/subscriptions-manager"} className="nav-link">
                      Subscriptions
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/ride-sessions"} className="nav-link">
                      RideSessions
                    </Link>
                  </li>
                </div>
            ) : ("")}
            {currentUser && currentUser.role.includes("ROLE_USER") ? (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/profile"} className="nav-link">
                      {currentUser.email}
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link" onClick={this.logOut}>
                      Log out
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/user-list-cities"} className="nav-link">
                      Where can you find us?
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/user-ride-sessions/" + currentUser.id} className="nav-link">
                      Ride history
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/user-subscriptions/" + currentUser.id} className="nav-link">
                      Subscriptions
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link to={"/order-ride/" + currentUser.id} className="nav-link">
                      Order ride
                    </Link>
                  </li>
                </div>
            ) : ("")}
          </nav>

          <div className="container mt-3">
            <Switch>
              <Route exact path="/login" component={Login} />
              <Route exact path="/register" component={Register} />
              <Route exact path="/profile" component={Profile} />
              <Route exact path="/cities" component={CitiesList} />
              <Route exact path="/scooters" component={ScooterList} />
              <Route path="/scooters/:id" component={Scooter} />
              <Route path="/add-scooter" component={ScooterAdd} />
              <Route path="/rental-points" component={RentalPointList} />
              <Route path="/scooter-rental-points/:id" component={RentalPoint} />
              <Route path="/add-rental-point" component={RentalPointAdd} />
              <Route exact path="/scooter-models" component={ScooterModelList} />
              <Route path="/scooter-models/:id" component={ScooterModel} />
              <Route path="/add-scooter-model" component={ScooterModelAdd} />
              <Route path="/cities-manager" component={CitiesManagerList} />
              <Route path="/add-city" component={CityAdd} />
              <Route path="/cities/:id" component={City} />
              <Route path="/subscriptions-manager" component={SubscriptionList} />
              <Route path="/add-subscription" component={SubscriptionAdd} />
              <Route path="/subscriptions/:id" component={Subscription} />
              <Route path="/users/:id" component={User} />
              <Route path="/users" component={UserList} />
              <Route path="/user-subscriptions/:id" component={UserSubscriptionList} />
              <Route path="/order-ride/:id" component={OrderRide} />
              <Route path="/ride-sessions" component={RideSessionsList} />
              <Route path="/user-ride-sessions/:id" component={UserRideSessionsList} />
              <Route path="/edit-profile/:id" component={UpdateUserProfile} />
              <Route path="/scooters-for-user/:id" component={UserScooterList} />
              <Route path="/user-scooter-rental-points/:id" component={UserRentalPointList} />
              <Route path="/user-list-cities" component={UserCityList} />
              <Route path="/ride-sessions-for-single-scooter/:id" component={RideSessionsForSingleScooter} />
            </Switch>
          </div>
        </div>
    );
  }
}

export default App;
