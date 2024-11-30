Ext.define('Luntan.view.courses.IndividualCourseTeacherController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.ictlist',
    reference: 'ictListController',


    onCreate: function()
    {
        var grid = this.getView(),
        	thisRegId = this.getViewModel().get('current.reg.id'),
        	theStore = grid.getStore(),
			itemNum = theStore.data.items.length;

		grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.IndCourseTeacher');
        r.set('assignmentId',thisRegId);
		r.set('department','IBG');

		var rec = theStore.insert(itemNum, r);
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
   },


	onStoreContentUpdated: function (theStore, theRecords) {
		console.log("Updated in controller")
/*
		this.up().getViewModel().data.current.reg.set('noExaminer',this.store.getCount());
		this.up().getViewModel().data.current.reg.set('note',this.store.getCount());
 */
	},

	onStoreContentRemoved: function (theStore, theRecords) {
		console.log("Removed in controller")
/*
		this.up().getViewModel().data.current.reg.set('noExaminer',this.store.getCount()-1);
		this.up().getViewModel().data.current.reg.set('note',this.store.getCount()-1);
 */
	},

	onCheckChange: function (checkbox, rowIndex, checked, record) {
		console.log("Check changed");
/* 
			var form   = checkbox.up('ictlist').findPlugin('rowediting').getEditor().form,
				fields  = [
					form.findField('fullDepartment'),
					form.findField('name'),
					form.findField('email'),
					form.findField('phone')
				],
			status = checked;
			if(status){
				fields.forEach(function(field){
					field.enable()
				});
				form.findField('ldapEntry').disable();

			} else {
				fields.forEach(function(field){
					field.disable()
				});
				form.findField('ldapEntry').enable();

			}
			return true;
 */
	},

	onNewTeacherKind: function ( theComboBox, newValue, oldValue ) {
		console.log("Teacher kind changed");
	
	},

	onNewTeacher: function ( theComboBox, newValue, oldValue ) {
		console.log("Teacher changed");

		var ts = this.getViewModel().get('teachers'),
			rec = this.getViewModel().data.currentICT,
			teach = ts.getById(newValue);
			
			if (teach != undefined) {
					rec.set('name',teach.get('name'));
					rec.set('fullDepartment', teach.get('fullDepartment'));
					rec.set('phone', teach.get('phone'));
					rec.set('email', teach.get('mail'));
			} else {
					rec.set('name','Personens namn');
					rec.set('fullDepartment', 'Personens institution och program');
					rec.set('phone', '018-471 1000');
					rec.set('email', 'studierektor-ibg@uu.se');
			}
	},

   	onBeforeRender: function (grid) {
   	},

   	init: function(view) {

		console.log("ICT controller init");

/* 
 		view.findPlugin('rowediting').addListener('checkChange', function(checkbox, rowIndex, checked, record) {
			console.log("ICT controller External check changed");
 		};
 */
 		
 
 		view.findPlugin('rowediting').addListener('beforeEdit', function(rowEditing, context) {
			/* Disabling editing of specific fileds */
			var form   = rowEditing.getEditor().form,
				fields  = [
					form.findField('fullDepartment'),
					form.findField('name'),
					form.findField('email'),
					form.findField('phone')
				],
			status = context.grid.up().getViewModel().get('current.ict.external');
			if(status){
				fields.forEach(function(field){
					field.enable()
				});
				form.findField('ldapEntry').disable();

			} else {
				fields.forEach(function(field){
					field.disable()
				});
				form.findField('ldapEntry').enable();

			}
			return true;
        });
 

   	}


});
