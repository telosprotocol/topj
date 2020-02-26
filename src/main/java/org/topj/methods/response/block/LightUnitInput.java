package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class LightUnitInput {
   @JSONField(name = "m_last_error")
   private Integer mLastError;
   @JSONField(name = "m_load")
   private Integer mLoad;
   @JSONField(name = "m_object_flags")
   private Integer mObjectFlags;
   @JSONField(name = "m_object_id")
   private Integer mObjectId;
   @JSONField(name = "m_object_type")
   private Integer mObjectType;

   public Integer getmLastError() {
       return mLastError;
   }

   public void setmLastError(Integer mLastError) {
       this.mLastError = mLastError;
   }

   public Integer getmLoad() {
       return mLoad;
   }

   public void setmLoad(Integer mLoad) {
       this.mLoad = mLoad;
   }

   public Integer getmObjectFlags() {
       return mObjectFlags;
   }

   public void setmObjectFlags(Integer mObjectFlags) {
       this.mObjectFlags = mObjectFlags;
   }

   public Integer getmObjectId() {
       return mObjectId;
   }

   public void setmObjectId(Integer mObjectId) {
       this.mObjectId = mObjectId;
   }

   public Integer getmObjectType() {
       return mObjectType;
   }

   public void setmObjectType(Integer mObjectType) {
       this.mObjectType = mObjectType;
   }
}
