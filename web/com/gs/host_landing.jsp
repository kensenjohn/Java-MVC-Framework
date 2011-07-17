
<link href="/web/css/jquery.datepick.css" rel="stylesheet" type="text/css" media="screen"/> 

<html>
<body>

	<div class="landing_when">
		<label class="m_b_txt">When is the Event?</label><br>
		<form id="frm_event_dt" name="frm_event_dt"  method="POST" action="START_EVENT_DATE.go">
			<input type="text" id="event_date" class="clearOnClick" name="event_date"><br>
		
			<input type="hidden" id="form_name_1" name="form_name_1" value="frm_event_dt">
		</form>
		<button id="event_dt_sbt" name="event_dt_sbt" value="Submit">Submit</button>
	</div>
</body>
<script type="text/javascript" src="/web/js/jquery.datepick.js"></script> 

	<script type="text/javascript">
	$(document).ready(function() {
		$("#event_date").datepick();
		$("#event_dt_sbt").click(function(event){submitEventDt()});

	});
	function submitEventDt()
	{
		$("#frm_event_dt").submit();
	}
	</script>
</html>
