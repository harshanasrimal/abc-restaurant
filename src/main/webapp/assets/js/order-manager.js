// Initialize cart from localStorage or create an empty cart
let cart = JSON.parse(localStorage.getItem('cart')) || [];

function nextStep(step) {
    // Hide all steps
    document.querySelectorAll('.step').forEach(function(stepDiv) {
        stepDiv.style.display = 'none';
    });

    // Show the current step
    document.getElementById('step' + step).style.display = 'block';
}

// Function to go back to the previous step
function previousStep(step) {
    nextStep(step);  // Reuse the nextStep function to show the previous step
}

// Function to show/hide delivery details based on the order type
function toggleDeliveryDetails() {
    const orderType = document.getElementById('orderType').value;
    if (orderType === 'delivery') {
        nextStep(3);  // Show delivery details step
    } else {
        nextStep(4);  // Skip to payment step if it's pickup
    }
}

// Function to render the cart items inside the modal
function renderCart() {
    const cartItemsContainer = document.getElementById('cart-items');
    const cartTotal = document.getElementById('cart-total');
    
    // If cart is empty, show the empty message
    if (cart.length === 0) {
        cartItemsContainer.innerHTML = '<p>Your cart is empty!</p>';
        cartTotal.innerHTML = '';
        return;
    }

    // Clear the cart items container
    cartItemsContainer.innerHTML = '';

    let totalPrice = 0;

    // Loop through each item in the cart and render it
    cart.forEach((item, index) => {
        const productTotal = item.price * item.quantity;
        totalPrice += productTotal;

        const cartItemHTML = `
            <div class="cart-item">
                <div class="cart-item-row">
                    <img src="${item.imageUrl}" alt="${item.name}" class="cart-item-image">
                    <div class="cart-item-details">
                        <p class="cart-item-name"><strong>${item.name}</strong></p>
                        <div class="cart-item-qty">
                            <button onclick="updateQuantity(${index}, -1)" class="qty-btn">-</button>
                            <span style="padding: 0 3px;">${item.quantity}</span>
                            <button onclick="updateQuantity(${index}, 1)" class="qty-btn">+</button>
                        </div>
                    </div>
                    <button class="btn remove-btn" onclick="removeFromCart(${index})">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </div>
                <p class="cart-item-price">Total: Rs${productTotal.toFixed(2)}</p>
            </div>
        `;
        cartItemsContainer.innerHTML += cartItemHTML;
    });

    // Display total price for the whole cart
    cartTotal.innerHTML = `<p>Sub Total: Rs${totalPrice.toFixed(2)}</p>`;
}

// Function to update the quantity of a cart item
function updateQuantity(index, delta) {
    cart[index].quantity += delta;

    // Ensure quantity doesn't go below 1
    if (cart[index].quantity < 1) {
        cart[index].quantity = 1;
    }

    // Update the cart in localStorage and re-render the cart
    localStorage.setItem('cart', JSON.stringify(cart));
    renderCart();
}

// Function to add a product to the cart
function addToCart(productId, name, price, imageUrl) {
    const product = cart.find(item => item.productId === productId);
    if (product) {
        product.quantity += 1; // Update quantity if product exists
    } else {
        cart.push({ productId, name, price, imageUrl, quantity: 1 });
    }

    // Update the cart in localStorage
    localStorage.setItem('cart', JSON.stringify(cart));
    renderCart(); // Re-render the cart
}

// Function to remove a product from the cart
function removeFromCart(index) {
    cart.splice(index, 1); // Remove item from the cart array
    localStorage.setItem('cart', JSON.stringify(cart)); // Update localStorage
    renderCart(); // Re-render the cart
}

// Function to handle checkout
function checkout() {
    const cartData = JSON.parse(localStorage.getItem('cart'));
    const cartString = cartData.map(item => `${item.productId}:${item.quantity}`).join(',');

    const deliveryAddress = document.getElementById('deliveryAddress').value;
    const branch = document.getElementById('branch').value;
    const orderType = document.getElementById('orderType').value;
    const paymentType = document.getElementById('paymentType').value;

    // For delivery-specific details
    const contactNumber = document.getElementById('contactNumber') ? document.getElementById('contactNumber').value : '';

    // Assign values to hidden fields in the form
    document.getElementById('addressInput').value = deliveryAddress;
    document.getElementById('branchInput').value = branch;
    document.getElementById('orderTypeInput').value = orderType;
    document.getElementById('paymentTypeInput').value = paymentType;

    if (orderType === 'delivery') {
        document.getElementById('contactNumberInput').value = contactNumber;
    }

    // Save cart data
    document.getElementById('cartDataInput').value = cartString;

    // Clear cart from localStorage and submit the form
    localStorage.removeItem('cart');
    document.getElementById('checkoutForm').submit();
}

// Initial render when the page loads
renderCart();
