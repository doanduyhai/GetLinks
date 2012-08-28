function generateLinks(data)
{
	$.ajax({
		type: HTTP_POST,
		url: "generateLinks",
		contentType: JSON_CONTENT,
		data: JSON.stringify(data),
		dataType: JSON_DATA,
		success: function(generatedQR) {

			loadQRCodePage(generatedQR);
		},
		error: function(jqXHR, textStatus, errorThrown)
		{
			console.log("Ajax error : "+errorThrown);
		}
	});
}

function loadQRCodePage(generatedQR)
{
	$('#generateLinkTab').click();
	$('#generateLinkPanel').empty();
	$('#generateLinkPanel').load('fragments/desktop/showQR.html #generatedQRContent',
	function()
	{
		$('#sharedNetworksList').val(generatedQR.networks.join(','));
		$('#generatedQR').attr('src','./getQR/'+generatedQR.uniqueTimeUUID);
		$('a.generatedQRLink').attr('href',generatedQR.url).html(generatedQR.url);
		$('a.generatedQRLinkWithPicture').attr('href',generatedQR.url);
	});
}
