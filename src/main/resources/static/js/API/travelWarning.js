$(function() {
	var contry = "US";
	MK_URL(contry);
})
function select_contry() {
	var contry = $("#selectContry").val();
	MK_URL(contry);
	$('#selectIOType').val(contry).prop("selected", true);
}
function travel_Warning(url) {
	$
		.ajax({
			url: url,
			type: "GET",
			dataType: "json",
			success: function(result) {
				var contry = result.data[0].country_nm + '('
					+ result.data[0].country_eng_nm + ')';
				var level = result.data[0].alarm_lvl;
				var region = result.data[0].region_ty;
				var remark = result.data[0].remark;
				var writtenDate = result.data[0].written_dt;
				var continent = result.data[0].continent_nm + '('
					+ result.data[0].continent_eng_nm + ')'

				empty_travel_Warning();

				$('.flag')
					.append(
						'<img style="height : 150px; width : 200px;" src=' + result.data[0].flag_download_url + '></img>');
				$('.continent').append(continent);
				$('.contry').append(contry);
				$('.level').append(level);
				$('.region').append(region);
				$('.remark').append(remark);
				$('.writtenDate').append(writtenDate);
				$('.map')
					.append(
						'<img src=' + result.data[0].dang_map_download_url + '></img>');
			}

		})
}
function MK_URL(contry) {
	var apiURLOption = "&pageNo=1&numOfRows=10&cond[country_iso_alp2::EQ]="
		+ contry + "&returnType=JSON";
	var apiURL = "http://apis.data.go.kr/1262000/TravelAlarmService2/getTravelAlarmList2?serviceKey=";
	$.ajax({
		url: "/mkTravelWarningURL",
		type: 'POST',
		data: {
			url: apiURL,
			option: apiURLOption
		},
		success: function(result) {

			travel_Warning(result);
		}

	})

}
function empty_travel_Warning() {
	$('.continent').empty();
	$('.contry').empty();
	$('.level').empty();
	$('.region').empty();
	$('.remark').empty();
	$('.writtenDate').empty();
	$('.map').empty();
	$('.flag').empty();
}