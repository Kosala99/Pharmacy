$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	
	$("#alertError").hide();
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateItemForm();
	
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var method = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "PharmacyAPI",
		type : method,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onItemSaveComplete(response.responseText, status);
		}
	});
});

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
	$("#billNo").val($(this).closest("tr").find('td:eq(0)').text());
	$("#patientName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(2)').text());
	$("#medicationDetails").val($(this).closest("tr").find('td:eq(3)').text());
	$("#doctorName").val($(this).closest("tr").find('td:eq(4)').text());
	$("#email").val($(this).closest("tr").find('td:eq(5)').text());
});

function onItemSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}

$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
		url : "PharmacyAPI",
		type : "DELETE",
		data : "billId=" + $(this).data("billid"),
		dataType : "text",
		complete : function(response, status)
		{
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

function onItemDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


function validateItemForm()
{
	// Bill no
	if ($("#billNo").val().trim() == "")
	{
		return "Insert Bill No.";
	}
	
	// P Name
	if ($("#patientName").val().trim() == "")
	{
		return "Insert Patient Name.";
	}
	
	//amount
	if ($("#amount").val().trim() == "")
	{
		return "Insert Amount.";
	}
	
	// is numerical value
	var tmpPrice = $("#amount").val().trim();
	
	if (!$.isNumeric(tmpPrice))
	{
		return "Insert a numerical value for amount.";
	}
	
	// convert to decimal price
	$("#amount").val(parseFloat(tmpPrice).toFixed(2));
	
	// medicationDetails
	if ($("#medicationDetails").val().trim() == "")
	{
		return "Insert Medication Details.";
	}
	
	// Doctor Name
	if ($("#doctorName").val().trim() == "")
	{
		return "Insert Doctor Name.";
	}
	
	
	// EMAIL
	if ($("#email").val().trim() == "")
	{
		return "Insert Email.";
	}
	
	
	return true;
}