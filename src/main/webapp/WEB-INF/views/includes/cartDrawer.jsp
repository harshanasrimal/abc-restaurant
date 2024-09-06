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
                <!-- Cart items go here -->                                                                                                                        
                <div id="cart-items">
                    <p>Your cart is empty!</p>
                </div>
                <!-- Total price -->
                <div id="cart-total"></div>
            </div> 
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="checkout()">Checkout</button>
            </div>                                                                                                                                       
        </div>                                                                                                                                                     
    </div>                                                                                                                                                         
</div>
