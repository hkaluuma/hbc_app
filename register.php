<?php
include "conn.php";
// Check connection

//capture data from front end of the app
$received_username = $_REQUEST['biz_username'];
$received_password = $_REQUEST['biz_password'];
$received_fullname = $_REQUEST['biz_fullname'];
$received_email = $_REQUEST['biz_email'];
$received_phonenumber = $_REQUEST['biz_phonenumber'];
$received_location = $_REQUEST['biz_selectedlocation'];

//$received_password = md5($password);

$sql = "INSERT INTO vht_officer (vht_username, vht_password, vht_fullname, vht_email, vht_phone_number, vht_location)
VALUES ('$received_username','$received_password','$received_fullname','$received_email','$received_phonenumber','$received_location')";

if ($conn->query($sql) === TRUE) {
    echo "1";
} else {
    echo "0";
}

$conn->close();

?>
