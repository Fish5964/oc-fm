<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="30%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="100px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-form-item
              prop="company.id"
              label="所属公司"
              :rules="[{ required: true, message: '所属公司不能为空', trigger: 'change' }]"
              class="dc-el-form-item_CascaderInput"
            >
              <el-cascader
                ref="cascader104"
                :options="companyOptions"
                v-model="editFormData.company.id"
                :style="{ width: '100%' }"
                placeholder="请选择公司"
                :props="{ label: 'name', value: 'id', children: 'children', checkStrictly: true, emitPath: false }"
                clearable
                filterable
                separator="/"
                class="dc-el-cascader_CascaderInput"
              ></el-cascader>
            </el-form-item>
            <el-form-item
              prop="code"
              label="编号"
              :rules="[{ required: true, message: '编号不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.code"
                :maxLength="64"
                placeholder="请输入编号"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="name"
              label="名称"
              :rules="[{ required: true, message: '名称不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.name"
                :maxLength="128"
                placeholder="请输入名称"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="parent.id"
              label="上级部门"
              class="dc-el-form-item_CascaderInput"
            >
              <el-cascader
                ref="parentCascader"
                :options="parentOptions"
                v-model="editFormData.parent.id"
                :style="{ width: '100%' }"
                placeholder="请选择父级"
                :props="{ label: 'name', value: 'id', children: 'children', checkStrictly: true, emitPath: false }"
                clearable
                filterable
                separator="/"
                class="dc-el-cascader_CascaderInput"
              ></el-cascader>
            </el-form-item>
            <el-form-item
              prop="sort"
              label="排序"
              :rules="[{ required: true, message: '排序不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.sort"
                :maxLength="21"
                placeholder="请输入排序"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item prop="director" label="总监" class="dc-el-form-item_SelectInput">
              <el-select
                v-model="editFormData.director"
                :style="{ width: '100%' }"
                placeholder="请选择总监"
                clearable
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in directorOptions" :key="index" :label="item.name" :value="item"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="manager" label="经理" class="dc-el-form-item_SelectInput">
              <el-select
                v-model="editFormData.manager"
                :style="{ width: '100%' }"
                placeholder="请选择经理"
                clearable
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in managerOptions" :key="index" :label="item.name" :value="item"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="remarks" label="备注信息" class="dc-el-form-item_MutilpleInput">
              <el-input
                v-model="editFormData.remarks"
                type="textarea"
                placeholder="请输入备注信息"
                rows="2"
                :maxLength="255"
                class="dc-el-input_MutilpleInput"
              ></el-input>
            </el-form-item>
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import { treeCompany } from '@/api/org/company.js'
import { treeDepartment } from '@/api/org/department.js'
import { listUserAll } from '@/api/admin/user.js'
/** 根据用户界面配置import组件 结束 */
import { getDepartmentById, saveDepartment } from '@/api/org/department'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'department-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      editFormData: this.initEditData(),
      /** 根据用户界面配置生成data数据 开始 */
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '部门管理.v2'
      },
      dialogTitle: '部门管理.v2',
      // 选项变量
      // 所属公司选项
      companyOptions: [],
      // 上级部门选项
      parentOptions: [],
      // 总监选项
      directorOptions: [],
      // 经理选项
      managerOptions: [],
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: ''
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  computed: {},
  methods: {
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      })
    },
    doSave() {
      this.setLoad()

      saveDepartment(this.editFormData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.showMessage({
              type: 'success',
              msg: '保存成功'
            })
            this.$emit('save-finished')
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    switchEdit() {
      this.action = 'edit'
      this.dialogProps.title = `修改${this.dialogTitle}`
      this.initOptions(this.editFormData)
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getDepartmentById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
              if (validatenull(form.parent)) {
                form.parent = {
                  id: null
                }
              }
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
            resolve(form)
          })
          .catch((error) => {
            this.outputError(error)
          })
      })
    },
    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },

    listCompanyOptions() {
      let search_List = {
        params: []
      }
      // 字段对应表上filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'id',
          queryType: '=',
          value: currentUser.company.id == '0' ? '' : currentUser.company.id
        }
      ])

      // 数据权限: 公司org_company
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1287908822026887245')

      treeCompany(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.companyOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listParentOptions() {
      let search_List = {
        params: []
      }
      // 字段对应表上filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 部门org_department
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1331142598487023634')

      treeDepartment(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.parentOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listDirectorOptions() {
      let search_List = {
        params: []
      }
      // 字段对应表上filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1289059804542828547')

      listUserAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.directorOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listManagerOptions() {
      let search_List = {
        params: []
      }
      // 字段对应表上filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1289059804542828547')

      listUserAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.managerOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listCompanyOptions()

      this.listParentOptions()

      this.listDirectorOptions()

      this.listManagerOptions()
    },
    onParentChange() {
      let nodes = this.$refs['parentCascader'].getCheckedNodes()
      if (nodes.length > 0) {
        if (nodes[0] && nodes[0].data && nodes[0].data.children) {
          this.editFormData.sort = (nodes[0].data.children.length + 1) * 10000
        } else if (nodes[0]) {
          this.editFormData.sort = 10000
        } else {
          this.editFormData.sort = (this.parentOptions.length + 1) * 10000
        }
      } else {
        this.editFormData.sort = (this.parentOptions.length + 1) * 10000
      }
    },
    initEditData(This) {
      return {
        // 所属公司
        company: {
          id: null,
          name: null
        },
        code: '', // 编号
        name: '', // 名称
        // 上级部门
        parent: {
          id: validatenull(This) || validatenull(This.id) ? null : This.id,
          name: validatenull(This) || validatenull(This.name) ? null : This.name
        },
        sort:
          This && This.children
            ? (This.children.length + 1) * 10000
            : this.parentOptions
            ? (this.parentOptions.length + 1) * 10000
            : 10000,
        director: {
          id: null,
          name: null
        },
        manager: {
          id: null,
          name: null
        },
        remarks: '' // 备注信息
      }
    }
  },
  watch: {
    parentOptions(newVal, oldVal) {
      if (newVal != oldVal && this.action == 'add') {
        this.$nextTick(() => {
          this.onParentChange()
        })
      }
    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.action = 'view'
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function (thisParent, parentTable) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(thisParent),
          ...parentTable
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openCopyDialog', async function (id) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.editFormData.id = null //把id设置为空，添加一个新的

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
