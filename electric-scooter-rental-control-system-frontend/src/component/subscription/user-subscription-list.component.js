import React, {Component} from "react";
import SubscriptionService from "../../service/subscription.service"

export default class UserSubscriptionList extends Component {
    constructor(props) {
        super(props);
        this.retrieveSubscriptions = this.retrieveSubscriptions.bind(this);
        this.setActiveSubscription = this.setActiveSubscription.bind(this);

        this.state = {
            subscriptions: [],
            currentSubscription: null,
            currentIndex: -1,
        };
    }

    componentDidMount() {
        this.retrieveSubscriptions();
    }

    retrieveSubscriptions() {
        SubscriptionService.getAllSubscriptionsByUser(this.props.match.params.id)
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
        SubscriptionService.getById(subscription.id)
            .then(response => {
                this.setState({
                    currentSubscription: response.data,
                    currentIndex: index
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { subscriptions, currentSubscription, currentIndex } = this.state;
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
                </div>
                <div className="col-md-6">
                    {currentSubscription ? (
                        <div><br/><br/>
                            <h4>Subscription</h4><br/>
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

