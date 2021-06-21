import React, {Component} from "react";
import ScooterService from "../../service/scooter.service"
import RideService from "../../service/ride.service"
import RentalPointService from "../../service/rental-point.service"
import UserService from "../../service/user.service"

export default class RideList extends Component {
    constructor(props) {
        super(props);
        this.retrieveRideSessions = this.retrieveRideSessions.bind(this);
        this.setActiveRideSession = this.setActiveRideSession.bind(this);

        this.state = {
            rideSessions: [],
            currentRideSession: null,
            currentIndex: -1,
            user: null,
            scooter: null
        };
    }

    componentDidMount() {
        this.retrieveRideSessions();
    }

    retrieveRideSessions() {
        RideService.getAll()
            .then(response => {
                this.setState({
                    rideSessions: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    setActiveRideSession(rideSession, index) {
        ScooterService.getScooter(rideSession.scooterId)
            .then(response => {
                this.setState({
                    scooter: response.data,
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
        UserService.getById(rideSession.userId)
            .then(response => {
                this.setState({
                    user: response.data,
                    currentRideSession: rideSession,
                    currentIndex: index,
                    scooter: this.state.scooter,
                    returnRentalPoint: this.state.returnRentalPoint
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { rideSessions, scooter, currentRideSession, currentIndex, user} = this.state;

        return (
            <div className="list row">
                <div className="col-md-6">
                    <h4>All ride sessions</h4><br/>

                    <ul className="list-group">
                        {rideSessions &&
                        rideSessions.map((rideSession, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveRideSession(rideSession, index)}
                                key={index}
                            >
                                Order date: {rideSession.createdOn.substring(0, rideSession.createdOn.indexOf("T"))}
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="col-md-6">
                    {currentRideSession ? (
                        <div><br/><br/>
                            <h4>Ride session</h4><br/>
                            <div>
                                <strong>User: </strong>{user==null ? "" : user.email}
                            </div><br/>
                            <div>
                                <strong>Scooter price per hour: </strong>{scooter==null ? "" : scooter.pricePerHour} BYN
                            </div><br/>
                            <div>
                                <strong>Price for ride: </strong>{currentRideSession.price} BYN
                            </div><br/>
                            <div>
                                <strong>Ride hours: </strong>{currentRideSession.rideHours} hours
                            </div><br/>
                            <div>
                                <strong>Order date: </strong>{currentRideSession.createdOn.substring(0, currentRideSession.createdOn.indexOf("T")) + " " + currentRideSession.createdOn.substring(11, 19)}
                            </div><br/>
                        </div>

                    ) : (
                        <div>
                            <br />
                            <p>Please click on ride session</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

