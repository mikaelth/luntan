Ext.define('Luntan.view.staff.UserModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.user',

    data: {
// 		current : {
// 			year : ''
// 		}
    },
    
    stores: {
		usertypes: 'UserRoleTypeStore'
	},
	
	formulas: {
/* 
        workingYear: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{comboCurrentYear.selection.label}', //--> reference configurated on the grid view (reference: comboCurrentYear)
                deep: true
            },
            get: function(year) {
            	this.set('current.year', year);
                return year;
            }
        }
 */
	}
});
