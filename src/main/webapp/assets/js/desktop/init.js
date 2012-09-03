!function ( $ ) {

	Getlinks = {};
	// left panel
	loadMyNetworks();
	loadAddNetwork();
	
    $('a[data-toggle="pill"]').on('shown', function(e) {
    	if (e.target.hash == '#myNetworkPanel') {
    		loadMyNetworks();
    	}
    	else if (e.target.hash == '#addNetworkPanel') 
    	{
    		
    	}
    	else if (e.target.hash == '#generateLinkPanel')
    	{
    		
    	}	
    });
    
	$(function() {

		$.ajaxSetup({
			statusCode: 
			{
				901 : function(){}
			}
		});

	   
	});

}( window.jQuery );



