function loadMyNetworks()
{
	$('#myNetworkPanel').empty();
	$('#myNetworkPanel').load('fragments/desktop/myNetworks.html #myNetworksContent',
	function()
	{
		$('.generateLinksButton').click(function()
		{
			var data = new Array();
			$('input[data-network]:checked').each(function(index,input)
			{
				var network = $(input).attr('data-network');
				data.push(network);
			});
			
			console.log("data = "+data);
			generateLinks(data);
		});
		
	});
}

function loadAddNetwork()
{
	$('#addNetworkPanel').empty();
	$('#addNetworkPanel').load('fragments/desktop/addNetworks.html #addNetworkContent',
	function()
	{
		$( "#myNetworksSelection" ).sortable({
			revert: true	
		});

		$('#scroll').anythingSlider({
			/* theme			: "metallic", */
			mode			: "horizontal",
			expand          : false,
			resizeContents  : false,    
			showMultiple	: 5,
			buildArrows     : false,
			buildNavigation : false,
			buildStartStop  : false,    
			enableArrows    : false,
			enableNavigation: false,
			enableStartStop : false,    
			startPanel      : 1,
			changeBy        : 1,
			infiniteSlides  : true,
			easing          : "linear",
			delay           : 50,
			animationTime   : 200,    
			/* appendBackTo    : "#networkList_prevBtn",
			appendForwardTo : "#networkList_nextBtn", */
			onInitialized: function(e, slider) {
				
				var forwardTimer,backwardTimer;
				
				
				$('#networkList_nextBtn').mouseenter(function()
				{
					forwardTimer = window.setInterval(function()
					{
						slider.goForward();
					},10);
					
					
				});
				
				$('#networkList_nextBtn').mouseleave(function()
				{
					window.clearInterval(forwardTimer);
				});
				
				$('#networkList_prevBtn').mouseenter(function()
				{
					backwardTimer = window.setInterval(function()
					{
						slider.goBack();
					},10);
				});
				
				$('#networkList_prevBtn').mouseleave(function()
				{
					window.clearInterval(backwardTimer);;
				});
			}
		});

		$('img.networkWidget').each(function(index,element)
		{
			$(element).tooltip({
				placement 	: 'top',
				title 		: $(element).attr('title')
			});
			
			
		});
		
		$('#scroll>li').each(function(index,element)
		{
			$(element).draggable(
			{
				connectToSortable	: "#myNetworksSelection",
				helper				: function()
				{

					var cloneLi = $(this).clone(true,true);
					var result = cloneLi
						.removeClass()
						.find('img')
						.removeClass('networkWidget')
						.removeAttr('rel')
						.end();
					
					/*
					cloneLi.find('img').tooltip({
						placement 	: 'top',
						title 		: 'test'//$(this).attr('data-original-title')
					});
					
					console.log('result = '+result[0].innerHTML);
					*/
					return result[0];
				},
				revert				: "invalid",
				opacity				: 0.7,
				refreshPositions	: true 
			});
		});
		
		$( "ul, li" ).disableSelection();
	});
}