import React, {Component} from "react";
import UserService from "../../service/user.service"
import SubscriptionService from "../../service/subscription.service"

export default class SubscriptionAdd extends Component {
    constructor(props) {
        super(props);
        this.getUsers = this.getUsers.bind(this);
        this.onChangeUser = this.onChangeUser.bind(this);
        this.onChangePrice = this.onChangePrice.bind(this);
        this.onChangeBalance = this.onChangeBalance.bind(this);
        this.onChangeDiscountPercentage = this.onChangeDiscountPercentage.bind(this);
        this.addSubscription = this.addSubscription.bind(this);

        this.state = {
            id: null,
            userId: null,
            price: 0.0,
            balance: 0.0,
            discountPercentage: 0.0,
            users: [],
            message: ""
        };
    }

    componentDidMount() {
        this.getUsers();
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
        this.setState({
            userId: e.target.value
        });
    }

    onChangePrice(e) {
        this.setState({
            price: e.target.value
        });
    }

    onChangeBalance(e) {
        this.setState({
            balance: e.target.value
        });
    }

    onChangeDiscountPercentage(e) {
        this.setState({
            discountPercentage: e.target.value
        });
    }

    addSubscription() {
        var data = {
            userId: this.state.userId,
            price: this.state.price,
            balance: this.state.balance,
            discountPercentage: this.state.discountPercentage,
            status: this.state.status
        }

        SubscriptionService.addSubscription(data)
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The subscription was added successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {users} = this.state;

        return (
            <div>
                <div className="edit-form">
                    <h4>Subscription</h4>
                    <form>
                        <div className="form-group">
                            <label>User: </label>
                            <select onChange={this.onChangeUser} id="scooterModelSelector">
                                <option value="Choose model"/>
                                {users &&
                                users.map((user) => (
                                    <option
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
                                defaultValue={this.state.price}
                                onChange={this.onChangePrice}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Balance: </label>
                            <input
                                type="text"
                                className="form-control"
                                id="description"
                                defaultValue={this.state.balance}
                                onChange={this.onChangeBalance}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Discount: </label>
                            <input
                                type="text"
                                className="form-control"
                                id="description"
                                defaultValue={this.state.discountPercentage}
                                onChange={this.onChangeDiscountPercentage}
                            />
                        </div>
                    </form>
                    <button
                        type="submit"
                        className="btn btn-primary btn-block updateButton"
                        onClick={this.addSubscription}
                    >
                        Save
                    </button>
                    <p>{this.state.message}</p>
                </div>
            </div>
        );
    }
}