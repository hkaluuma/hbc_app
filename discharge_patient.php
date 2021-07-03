<?php
include "conn.php";
// Check connection

//capture data from front end of the app
//$received_fullname = $_REQUEST['biz_fullnames'];
$received_fullname = $_POST["biz_fullnames"];

$sql = "UPDATE patient SET p_status='discharged' WHERE p_fullnames = '$received_fullname' "; 

//$sql = "INSERT INTO vht_officer (vht_username, vht_password, vht_fullname, vht_email, vht_phone_number, vht_location)
//VALUES ('$received_username','$received_password','$received_fullname','$received_email','$received_phonenumber','$received_location')";

if ($conn->query($sql) === TRUE) {
    echo "1";
} else {
    echo "0";
}

$conn->close();

?>
