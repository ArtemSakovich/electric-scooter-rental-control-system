import React, {Component} from "react";
import {Link} from "react-router-dom";
import SubscriptionService from "../../service/subscription.service"
import UserService from "../../service/user.service"

export default class SubscriptionList extends Component {
    constructor(props) {
        super(props);
        this.retrieveSubscriptions = this.retrieveSubscriptions.bind(this);
        this.setActiveSubscription = this.setActiveSubscription.bind(this);

        this.state = {
            subscriptions: [],
            currentSubscription: null,
            currentIndex: -1,
            user: null
        };
    }

    componentDidMount() {
        this.retrieveSubscriptions();
    }

    retrieveSubscriptions() {
        SubscriptionService.getAll()
            .then(response => {
                this.setState({
                    subscriptions: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    setActiveSubscription(subscription, index) {
        UserService.getById(subscription.userId)
            .then(response => {
                this.setState({
                    user: response.data,
                    currentSubscription: subscription,
                    currentIndex: index
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { subscriptions, currentSubscription, currentIndex, user} = this.state;
        return (
            <div className="list row">
                <div className="col-md-6">
                    <h4>All subscriptions</h4><br/>

                    <ul className="list-group">
                        {subscriptions &&
                        subscriptions.map((subscription, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveSubscription(subscription, index)}
                                key={index}
                            >
                                <strong>{subscription.status}</strong> - Discount: {subscription.discountPercentage}%
                            </li>
                        ))}
                    </ul>
                    <Link
                        to={"/add-subscription"}
                        className="btn btn-primary btn-block updateButton"
                    >
                        Add subscription
                    </Link>
                </div>
                <div className="col-md-6">
                    {currentSubscription ? (
                        <div><br/><br/>
                            <h4>Subscription</h4><br/>
                            <div>
                                <strong>User: </strong>{user==null ? "" : user.email}
                            </div><br/>
                            <div>
                                <strong>Price: </strong>{currentSubscription.price} BYN
                            </div><br/>
                            <div>
                                <strong>Balance: </strong>{currentSubscription.balance} BYN
                            </div><br/>
                            <div>
                                <strong>Discount: </strong>{currentSubscription.discountPercentage}%
                            </div><br/>
                            <div>
                                <strong>Status: </strong>{currentSubscription.status}
                            </div><br/>
                            <Link
                                to={"/subscriptions/" + currentSubscription.id}
                                className="btn btn-primary btn-block editButton"
                            >
                                Edit
                            </Link>
                        </div>

                    ) : (
                        <div>
                            <br />
                            <p>Please click on subscription</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

