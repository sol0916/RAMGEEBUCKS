--table »ý¼º
CREATE TABLE authorities (
    authority_no NUMBER PRIMARY KEY,
    authorityName VARCHAR2(50) NOT NULL
);
CREATE TABLE members (
    member_no NUMBER PRIMARY KEY,
    username VARCHAR2(50) NOT NULL,
    password VARCHAR2(50) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) NOT NULL,
    phoneNumber VARCHAR2(20) NOT NULL,
    authority_no NUMBER REFERENCES authorities(authority_no)
);

CREATE TABLE products (
    product_no NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    price NUMBER(10) NOT NULL,
    description VARCHAR2(500) NOT NULL,
    amount NUMBER NOT NULL
);
CREATE TABLE carts (
    cart_no NUMBER PRIMARY KEY,
    member_no NUMBER REFERENCES members(member_no)
);
CREATE TABLE orders (
    order_no NUMBER PRIMARY KEY,
    cart_no NUMBER REFERENCES carts(cart_no),
    order_date DATE NOT NULL,
    total_amount NUMBER(10) NOT NULL,
    status VARCHAR2(50) NOT NULL
);
CREATE TABLE pays (
    pay_no NUMBER PRIMARY KEY,
    order_no NUMBER REFERENCES orders(order_no),
    amountPaid NUMBER(10) NOT NULL,
    paymentDate DATE NOT NULL
);
CREATE TABLE cart_order (
    cart_no NUMBER REFERENCES carts(cart_no),
    order_no NUMBER REFERENCES orders(order_no),
    PRIMARY KEY (cart_no, order_no)
);
CREATE TABLE order_product (
    order_no NUMBER REFERENCES orders(order_no),
    product_no NUMBER REFERENCES products(product_no),
    quantity NUMBER NOT NULL,
    PRIMARY KEY (order_no, product_no)
);

-- ½ÃÄö½º 
CREATE SEQUENCE member_no_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE cart_no_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE order_no_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE pay_no_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE product_no_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE authority_no_seq START WITH 1 INCREMENT BY 1;

commit;