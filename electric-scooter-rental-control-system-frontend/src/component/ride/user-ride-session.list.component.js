import React, {Component} from "react";
import ScooterService from "../../service/scooter.service"
import RideService from "../../service/ride.service"
import UserService from "../../service/user.service"


export default class UserRideList extends Component {
    constructor(props) {
        super(props);
        this.retrieveRideSessions = this.retrieveRideSessions.bind(this);
        this.setActiveRideSession = this.setActiveRideSession.bind(this);

        this.state = {
            rideSessions: [],
            currentRideSession: null,
            currentIndex: -1,
            scooter: null
        };
    }

    componentDidMount() {
        this.retrieveRideSessions();
    }

    retrieveRideSessions() {
        RideService.getAllByUser(this.props.match.params.id)
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
                    currentRideSession: rideSession,
                    currentIndex: index,
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

