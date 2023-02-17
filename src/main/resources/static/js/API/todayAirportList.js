$(function() {
	var pagenum = 1;
	var schIOType = "O";
	var schAirCode = "GMP";
	var apiURLOption = "&schIOType=" + schIOType + "&schAirCode="
		+ schAirCode + "&pageNo=" + pagenum;
	airportList(pagenum, apiURLOption);
	$('#selectAirport').val(schAirCode).prop("selected", true);
	$('#selectIOType').val(schIOType).prop("selected", true);
});
function airportListChange(el) {
	var pagenum = $(el).val();
	var schIOType = $("#selectIOType").val();
	var schAirCode = $("#selectAirport").val();
	if (schIOType == "both") {
		var apiURLOption = "&schAirCode=" + schAirCode + "&pageNo="
			+ pagenum;
		airportList(pagenum, apiURLOption);
	} else {
		var apiURLOption = "&schIOType=" + schIOType + "&schAirCode="
			+ schAirCode + "&pageNo=" + pagenum;
		airportList(pagenum, apiURLOption);
	}

	airportList(pagenum, apiURLOption);
	$('#selectAirport').val(schAirCode).prop("selected", true);
	$('#selectIOType').val(schIOType).prop("selected", true);
}

function airportList(pagenum, apiURLOption) {
	var apiURL = "http://openapi.airport.co.kr/service/rest/FlightStatusList/getFlightStatusList?ServiceKey=";
	$.ajax({
		url: "/todayAirport",
		type: 'POST',
		data: {
			url: apiURL,
			option: apiURLOption
		},
		success: function(data) {
			$("#display").html(data);
		}
	});
}