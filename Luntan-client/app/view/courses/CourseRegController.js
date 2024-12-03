Ext.define('Luntan.view.courses.CourseRegController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.coursereglist',

    onSelectionChange: function(sm, rec) {
		var deleteButton = this.lookupReference('btnRemove'),
			cbId = rec[0].get('courseInstanceId');
			ci = this.getViewModel().get('icis').getById( cbId );
		
		console.log(ci);
		console.log( cbId );
				
		if (ci.get('registrationValid')) {
			deleteButton.disable();
		} else {
			deleteButton.enable();
		}


    
    },


    onCreate: function()
    {
        var grid = this.getView(),
        	thisEDoc = this.getViewModel().get('current.edoc.id');
         grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.IndCourseReg');
        r.set('economyDocId',thisEDoc);
        r.set('registrationDate',new Date());
		r.set('ibgReg',true);
		
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);

    },

   	onBeforeRender: function (grid) {
   	},

   	onUpdateInactive: function () {
   	},

   	onIndCourseTeachers: function () {
		console.log("onIndCourseTeachers");
   		var year = this.getViewModel().get('current.edoc.year');
   		window.open(Luntan.data.Constants.BASE_URL.concat('view/ictds?year=').concat(year));
   	},

   	init: function (view) {

		var tb = view.down('toolbar');

		tb.insert(2,
			{
				xtype: 'button',
				showText: true,
				tooltip:'Visa lärare inblandade i individuella kurser för aktuellt år',
				text: 'Sammanställning lärare',
				reference: 'btnIndCourseTeachers',
				disabled: true,
				listeners: {
				  click: 'onIndCourseTeachers'
				}
			});

		view.lookupReference('comboCurrentYear').addListener('select', function(combo,record) {
			this.lookupReferenceHolder().lookupReference('btnIndCourseTeachers').enable();
			return true;
        });

/*

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
 
  		view.findPlugin('rowediting').addListener('beforeEdit', function(rowEditing, context) {
			/* Disabling editing of specific fileds */
			var form   = rowEditing.getEditor().form,
				fields  = [
					form.findField('startDate'),
					form.findField('courseInstanceId'),
					form.findField('studentName'),
					form.findField('ibgReg')
				],
				icbId = context.record.get('courseInstanceId'),
				creditBasisId = context.record.get('creditBasisRecId'),
				vM = context.grid.up().getViewModel();
				ci = vM.get('icis').getById(icbId),
				creditBasis = vM.get('credbasis').getById(creditBasisId);
				
			var status = !ci.get('registrationValid') && ( (creditBasis == null) || creditBasis.get('sent') == null );

			if(status){
				fields.forEach(function(field){
					field.enable()
				});
//				form.findField('ldapEntry').disable();

			} else {
				fields.forEach(function(field){
					field.disable()
				});
//				form.findField('ldapEntry').enable();

			}
			return true;
        });

	}

});
