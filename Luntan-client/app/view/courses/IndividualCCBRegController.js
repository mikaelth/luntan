Ext.define('Luntan.view.courses.IndividualCCBRegController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.credbasereglist',

 /*    
    onCreate: function()
    {

        var grid = this.getView(),
        	thisCCB = this.getViewModel().get('current.cbase.id');
         grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.IndCourseCreditBasis');
//        r.set('economyDocId',thisEDoc);

		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },
 */

   	onBeforeRender: function (grid) {
   	},

   	onUpdateInactive: function () {
   	},

   	init: function (view) {

		var tb = view.down('toolbar');
		
		tb.remove(view.lookupReference('btnCreate'));
		tb.remove(view.lookupReference('btnRemove'));
/* 						
		tb.insert(2,
			{
				xtype: 'button',
				showText: true,
				tooltip:'Slå upp kurser i studieplaner',
				text: 'Kurser per program',
				reference: 'btnProgramCourses',
				disabled: false,
				listeners: {
				  click: 'onProgramCourses'
				}
			});			


		
		tb.insert(1,
			{
				xtype: 'button',
				showText: true,
				tooltip:'Kontrollerar om det i kursplanen anges att Kursen är avvecklad',
				text: 'Uppdatera inaktiva',
				reference: 'btnInactiveUpdate',
				disabled: false,
				listeners: {
				  click: 'onUpdateInactive'
				}
			});			
        
 */

		console.log("ICCBR controller init");

	}

});
