<?php 
//include('connx.php');
include('conn.php');


$stmt = $conn->prepare("SELECT p_fullnames, p_comments, patient_id, p_test_status, p_location, p_age, p_phonenumber, p_disease, p_img  FROM patient WHERE p_status='sick' ORDER BY p_date_created ASC");
$stmt->execute();
$stmt-> bind_result($p_fullnames, $p_comments, $patient_id, $p_test_status, $p_location, $p_age, $p_phonenumber, $p_disease, $p_img);

$details = array();
while($stmt ->fetch()){
	$temp = array();
	$temp['fullnames'] = $p_fullnames;
	$temp['comments'] = $p_comments;
	$temp['patient_id'] = $patient_id;
	$temp['test_status'] = $p_test_status;
	$temp['location'] = $p_location;
	$temp['age'] = $p_age;
	$temp['phonenumber'] = $p_phonenumber;
	$temp['disease'] = $p_disease;
	$temp['img'] = $p_img;

array_push($details, $temp);
}

echo json_encode($details);
?>