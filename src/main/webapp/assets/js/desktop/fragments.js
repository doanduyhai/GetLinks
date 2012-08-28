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
