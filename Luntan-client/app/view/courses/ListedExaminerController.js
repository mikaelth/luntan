Ext.define('Luntan.view.courses.ListedExaminerController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.listedexaminerlist',

    
    onCreate: function()
    {
        var grid = this.getView(), 
        	thisCourseId = this.getViewModel().get('current.cid'),
        	theStore = grid.getStore(),
			itemNum = theStore.data.items.length;
			
		grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.Examiner');
		r.set('rank', theStore.nextRank());
        r.set('courseId',thisCourseId);
        r.set('decided',false);
        r.set('decisionId',null);
		
		
		var rec = theStore.insert(itemNum, r);
//		grid.ownerGrid.refreshRank();
        grid.plugins[0].startEdit(rec[0]);
        
    },

   onRemove: function()
    {
        var grid = this.getView();
        var sm = grid.getSelectionModel();
            grid.plugins[0].cancelEdit();
            grid.getStore().remove(sm.getSelection());
            if (grid.getStore().getCount() > 0) {
                sm.select(0);
            }
		grid.ownerGrid.refreshRank();
   },
   onSendBrd: function () 
   {
/* 
		vm = this.getViewModel(),
		dest = vm.get('edBrd'),

		if(vm.get('itemstorage').getById(dest) !== null) {
			sm.getSelected().each(function(item) {
					item.set('storageId',dest);
			});

			sm.getStore().getSource().sync();
			this.getViewModel().set('mvDestStorage',0);
	        this.lookupReference('btnMove').disable();

		}
 */
   },

   	onBeforeRender: function (grid) {
   	},
   	
   	init: function(view) {

   	
   	}

    
});
