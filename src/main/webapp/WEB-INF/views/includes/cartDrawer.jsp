<div class="modal right fade" id="cartDrawer" tabindex="-1" role="dialog" aria-labelledby="cartDrawerLabel" aria-hidden="true">                                    
    <div class="modal-dialog" role="document">                                                                                                                     
        <div class="modal-content">                                                                                                                                
            <div class="modal-header">                                                                                                                             
                <h5 class="modal-title" id="cartDrawerLabel">Shopping Cart</h5>                                                                                    
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">                                                                       
                    <span aria-hidden="true">&times;</span>                                                                                                        
                </button>                                                                                                                                          
            </div>                                                                                                                                                 
            <div class="modal-body">                                                                                                                               
                <!-- Step 1: Cart Items -->
                <div id="step1" class="step">
                    <div id="cart-items">
                        <p>Your cart is empty!</p>
                    </div>
                    <!-- Total price -->
                    <div id="cart-total"></div>
<c:choose>
    <c:when test="${not empty loggedInUser}">
        <button type="button" class="btn btn-primary" onclick="nextStep(2)">Proceed to Delivery</button>
    </c:when>
    <c:otherwise>
        <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/auth/?action=login">Login to Continue</a>
    </c:otherwise>
</c:choose>
                </div>

                <!-- Step 2: Delivery Details -->
                <div id="step2" class="step" style="display:none;">
                    <h6>Delivery Details</h6>
                    <div class="form-group">
                        <label for="deliveryAddress">Address</label>
                        <input type="text" class="form-control" id="deliveryAddress" name="deliveryAddress" placeholder="Enter delivery address">
                    </div>
                    <div class="form-group">
                        <label for="branch">Branch(should automate)</label>
                        <select class="form-control" id="branch" name="branch">
                            <option value="1">Kandy</option>
                            <option value="2">Colombo</option>
                        </select>
                    </div>
                    <button type="button" class="btn btn-primary" onclick="nextStep(3)">Proceed to Order Type</button>
                    <button type="button" class="btn btn-secondary" onclick="previousStep(1)">Back</button>
                </div>

                <!-- Step 3: Order Type -->
                <div id="step3" class="step" style="display:none;">
                    <h6>Order Type</h6>
                    <div class="form-group">
                        <label for="orderType">Order Type</label>
                        <select class="form-control" id="orderType" name="orderType">
                            <option value="delivery">Delivery</option>
                            <option value="pickup">Pickup</option>
                        </select>
                    </div>
                    <button type="button" class="btn btn-primary" onclick="nextStep(4)">Proceed to Payment</button>
                    <button type="button" class="btn btn-secondary" onclick="previousStep(2)">Back</button>
                </div>

                <!-- Step 4: Payment Type -->
                <div id="step4" class="step" style="display:none;">
                    <h6>Payment Type</h6>
                    <div class="form-group">
                        <label for="paymentType">Payment Type</label>
                        <select class="form-control" id="paymentType" name="paymentType">
                            <option value="credit">Credit Card</option>
                            <option value="cash">Cash</option>
                        </select>
                    </div>
                    <button type="button" class="btn btn-primary" onclick="checkout()">Submit Order</button>
                    <button type="button" class="btn btn-secondary" onclick="previousStep(3)">Back</button>
                </div>
            </div> 

            <div class="modal-footer">
                <form id="checkoutForm" method="POST" action="${pageContext.request.contextPath}/checkout">
                    <input type="hidden" name="cartData" id="cartDataInput">
                    <input type="hidden" name="address" id="addressInput">
                    <input type="hidden" name="branch" id="branchInput">
                    <input type="hidden" name="orderType" id="orderTypeInput">
                    <input type="hidden" name="paymentType" id="paymentTypeInput">
                </form>
            </div>                                                                                                                                       
        </div>                                                                                                                                                     
    </div>                                                                                                                                                         
</div>
