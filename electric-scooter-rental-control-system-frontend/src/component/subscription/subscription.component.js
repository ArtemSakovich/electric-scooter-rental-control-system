import React, {Component} from "react";
import SubscriptionService from "../../service/subscription.service"
import UserService from "../../service/user.service";


export default class Subscription extends Component {
    constructor(props) {
        super(props);
        this.getSubscription = this.getSubscription.bind(this);
        this.getUsers = this.getUsers.bind(this);
        this.onChangeUser = this.onChangeUser.bind(this);
        this.onChangePrice = this.onChangePrice.bind(this);
        this.onChangeBalance = this.onChangeBalance.bind(this);
        this.onChangeDiscountPercentage = this.onChangeBalance.bind(this);
        this.updateSubscription = this.updateSubscription.bind(this);
        this.deleteSubscription = this.deleteSubscription.bind(this);

        this.state = {
            currentSubscription: {
                id: null,
                userId: null,
                price: 0.0,
                balance: 0.0,
                discountPercentage: 0.0,
            },
            users: [],
            message: ""
        };
    }

    componentDidMount() {
        this.getUsers();
        this.getSubscription(this.props.match.params.id);
    }

    getSubscription(id) {
        SubscriptionService.getById(id)
            .then(response => {
                this.setState({
                    currentSubscription: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    getUsers() {
        UserService.getAll()
            .then(response => {
                this.setState({
                    users: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    onChangeUser(e) {
        const newUser = e.target.value;
        this.setState(function(prevState) {
            return {
                currentSubscription: {
                    ...prevState.currentSubscription,
                    userId: newUser
                }
            };
        });
    }

    onChangePrice(e) {
        const newPrice = e.target.value;
        this.setState(prevState => ({
            currentSubscription: {
                ...prevState.currentSubscription,
                price: newPrice
            }
        }));
        console.log(this.state)
    }

    onChangeBalance(e) {
        const newBalance = e.target.value;
        this.setState(prevState => ({
            currentSubscription: {
                ...prevState.currentSubscription,
                balance: newBalance
            }
        }));
    }

    onChangeDiscountPercentage(e) {
        const newDiscountPercentage = e.target.value;

        this.setState(prevState => ({
            currentSubscription: {
                ...prevState.currentSubscription,
                discountPercentage: newDiscountPercentage
            }
        }));
    }

    updateSubscription() {
        SubscriptionService.update(
            this.state.currentSubscription
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The subscription was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    deleteSubscription() {
        SubscriptionService.delete(this.state.currentSubscription.id)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The subscription was deleted successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentSubscription, users} = this.state;
        return (
            <div>
                {currentSubscription ? (
                    <div className="edit-form">
                        <h4>Subscription</h4>
                        <form>
                            <div className="form-group">
                                <label>User: </label>
                                <select onChange={this.onChangeUser} id="scooterModelSelector">
                                    {users &&
                                    users.map((user) => (
                                        <option selected={currentSubscription.userId === user.id}
                                                value={user.id}>{user.email}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-group">
                                <label htmlFor="description">Price: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="description"
                                    value={currentSubscription.price}
                                    onChange={this.onChangePrice}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="passedHours">Balance: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="passedHours"
                                    value={currentSubscription.balance}
                                    onChange={this.onChangeBalance}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="passedHours">Discount: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="passedHours"
                                    value={currentSubscription.discountPercentage}
                                    onChange={this.onChangeDiscountPercentage}
                                />
                            </div>
                        </form>
                        <button
                            className="btn btn-primary btn-block deleteButton"
                            onClick={this.deleteSubscription}
                        >
                            Delete
                        </button>
                        <button
                            type="submit"
                            className="btn btn-primary btn-block updateButton"
                            onClick={this.updateSubscription}
                        >
                            Update
                        </button>
                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on a subscription...</p>
                    </div>
                )}
            </div>
        );
    }
}