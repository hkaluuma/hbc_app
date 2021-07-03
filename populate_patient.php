<?php
require_once "conn.php";
$sql ="SELECT patient_id, p_fullnames FROM patient WHERE p_status='sick' ORDER BY p_date_created ASC";

//if($conn->query($sql)){
	//echo "Error in connecting to Database.";
//}else{
	$result = $conn->query($sql);
	if($result->num_rows > 0){
		$return_arr['patients'] = array();
		while($row = $result->fetch_array()) {
			array_push($return_arr['patients'], array(
						'patient_id'=>$row['patient_id'],
						'p_fullnames'=>$row['p_fullnames']
			));
		}
		echo json_encode($return_arr);
	}
//}
?>