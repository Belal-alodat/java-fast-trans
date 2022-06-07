INSERT INTO `shipment_status` (`id`, `description`, `name`) VALUES
(0, 'Customer_Submitted', 'Customer_Submitted'),
(1, 'Operator_Assigned_For_Picking', 'Operator_Assigned_For_Picking'),
(2, 'Driver_Picked', 'Driver_Picked'),
(3, 'Operator_Assigned_For_Delivery', 'Operator_Assigned_For_Delivery'),
(4, 'Driver_Delivered', 'Driver_Delivered'),
(5, 'Customer_Canceled', 'Customer_Canceled'),
(6, 'Customer_Rejected', 'Customer_Rejected'),
(7, 'Customer_Accepted', 'Customer_Accepted'),
(8, 'Operator_Rejected', 'Operator_Rejected'),
(9, 'Operator_Accepted', 'Operator_Accepted'),
(10, 'Operator_Store_Rejected', 'Operator_Store_Rejected'),
(11, 'Operator_Store_Accepted', 'Operator_Store_Accepted'),
(12, 'Driver_pick_Accepted', 'Driver_pick_Accepted'),
(13, 'Driver_pick_Rejected', 'Driver_pick_Rejected'),
(14, 'Driver_Stored', 'Driver_Stored'),
(15, 'Driver_deliver_Accepted', 'Driver_deliver_Accepted'),
(16, 'Driver_deliver_Rejected', 'Driver_deliver_Rejected');

INSERT INTO `dimension` (`id`, `description`, `name`) VALUES
(-1, 't', 't');
INSERT INTO `city` (`id`, `description`, `name`) VALUES
(-1, 't', 't'),
(2, 'irbid', 'irbid');

INSERT INTO `product` (`id`, `description`, `name`) VALUES
(-1, 't', 't');


INSERT INTO `town` (`id`, `description`, `name`, `city_id`) VALUES
(-1, 't', 't', -1),
(2, 'taibah', 'taibah', 2);

INSERT INTO `village` (`id`, `description`, `name`, `town_id`) VALUES
(-1, 't', 't', -1),
(2, 'Dear', 'Dear', 2);

INSERT INTO `account` (`id`, `active`, `email`, `full_name`, `mobile`, `password`, `role_name`) VALUES ('-1', '1', 'o@com', 'Operator', '0', '123123', 'OPERATOR');