// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ProfileService.proto

package com.examples.lib;

/**
 * Protobuf type {@code com.example.RemoveGroupFromProfileMessage}
 */
public final class RemoveGroupFromProfileMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.example.RemoveGroupFromProfileMessage)
    RemoveGroupFromProfileMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use RemoveGroupFromProfileMessage.newBuilder() to construct.
  private RemoveGroupFromProfileMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RemoveGroupFromProfileMessage() {
    session_ = "";
    profileId_ = "";
    groupId_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new RemoveGroupFromProfileMessage();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private RemoveGroupFromProfileMessage(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            session_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            profileId_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            groupId_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.examples.lib.ProfileServiceProto.internal_static_com_example_RemoveGroupFromProfileMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.examples.lib.ProfileServiceProto.internal_static_com_example_RemoveGroupFromProfileMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.examples.lib.RemoveGroupFromProfileMessage.class, com.examples.lib.RemoveGroupFromProfileMessage.Builder.class);
  }

  public static final int SESSION_FIELD_NUMBER = 1;
  private volatile java.lang.Object session_;
  /**
   * <code>string session = 1;</code>
   * @return The session.
   */
  @java.lang.Override
  public java.lang.String getSession() {
    java.lang.Object ref = session_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      session_ = s;
      return s;
    }
  }
  /**
   * <code>string session = 1;</code>
   * @return The bytes for session.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getSessionBytes() {
    java.lang.Object ref = session_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      session_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PROFILEID_FIELD_NUMBER = 2;
  private volatile java.lang.Object profileId_;
  /**
   * <code>string profileId = 2;</code>
   * @return The profileId.
   */
  @java.lang.Override
  public java.lang.String getProfileId() {
    java.lang.Object ref = profileId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      profileId_ = s;
      return s;
    }
  }
  /**
   * <code>string profileId = 2;</code>
   * @return The bytes for profileId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getProfileIdBytes() {
    java.lang.Object ref = profileId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      profileId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int GROUPID_FIELD_NUMBER = 3;
  private volatile java.lang.Object groupId_;
  /**
   * <code>string groupId = 3;</code>
   * @return The groupId.
   */
  @java.lang.Override
  public java.lang.String getGroupId() {
    java.lang.Object ref = groupId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      groupId_ = s;
      return s;
    }
  }
  /**
   * <code>string groupId = 3;</code>
   * @return The bytes for groupId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getGroupIdBytes() {
    java.lang.Object ref = groupId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      groupId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(session_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, session_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(profileId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, profileId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(groupId_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, groupId_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(session_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, session_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(profileId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, profileId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(groupId_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, groupId_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.examples.lib.RemoveGroupFromProfileMessage)) {
      return super.equals(obj);
    }
    com.examples.lib.RemoveGroupFromProfileMessage other = (com.examples.lib.RemoveGroupFromProfileMessage) obj;

    if (!getSession()
        .equals(other.getSession())) return false;
    if (!getProfileId()
        .equals(other.getProfileId())) return false;
    if (!getGroupId()
        .equals(other.getGroupId())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + SESSION_FIELD_NUMBER;
    hash = (53 * hash) + getSession().hashCode();
    hash = (37 * hash) + PROFILEID_FIELD_NUMBER;
    hash = (53 * hash) + getProfileId().hashCode();
    hash = (37 * hash) + GROUPID_FIELD_NUMBER;
    hash = (53 * hash) + getGroupId().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.examples.lib.RemoveGroupFromProfileMessage parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.examples.lib.RemoveGroupFromProfileMessage prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.example.RemoveGroupFromProfileMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.example.RemoveGroupFromProfileMessage)
      com.examples.lib.RemoveGroupFromProfileMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.examples.lib.ProfileServiceProto.internal_static_com_example_RemoveGroupFromProfileMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.examples.lib.ProfileServiceProto.internal_static_com_example_RemoveGroupFromProfileMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.examples.lib.RemoveGroupFromProfileMessage.class, com.examples.lib.RemoveGroupFromProfileMessage.Builder.class);
    }

    // Construct using com.examples.lib.RemoveGroupFromProfileMessage.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      session_ = "";

      profileId_ = "";

      groupId_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.examples.lib.ProfileServiceProto.internal_static_com_example_RemoveGroupFromProfileMessage_descriptor;
    }

    @java.lang.Override
    public com.examples.lib.RemoveGroupFromProfileMessage getDefaultInstanceForType() {
      return com.examples.lib.RemoveGroupFromProfileMessage.getDefaultInstance();
    }

    @java.lang.Override
    public com.examples.lib.RemoveGroupFromProfileMessage build() {
      com.examples.lib.RemoveGroupFromProfileMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.examples.lib.RemoveGroupFromProfileMessage buildPartial() {
      com.examples.lib.RemoveGroupFromProfileMessage result = new com.examples.lib.RemoveGroupFromProfileMessage(this);
      result.session_ = session_;
      result.profileId_ = profileId_;
      result.groupId_ = groupId_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.examples.lib.RemoveGroupFromProfileMessage) {
        return mergeFrom((com.examples.lib.RemoveGroupFromProfileMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.examples.lib.RemoveGroupFromProfileMessage other) {
      if (other == com.examples.lib.RemoveGroupFromProfileMessage.getDefaultInstance()) return this;
      if (!other.getSession().isEmpty()) {
        session_ = other.session_;
        onChanged();
      }
      if (!other.getProfileId().isEmpty()) {
        profileId_ = other.profileId_;
        onChanged();
      }
      if (!other.getGroupId().isEmpty()) {
        groupId_ = other.groupId_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.examples.lib.RemoveGroupFromProfileMessage parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.examples.lib.RemoveGroupFromProfileMessage) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object session_ = "";
    /**
     * <code>string session = 1;</code>
     * @return The session.
     */
    public java.lang.String getSession() {
      java.lang.Object ref = session_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        session_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string session = 1;</code>
     * @return The bytes for session.
     */
    public com.google.protobuf.ByteString
        getSessionBytes() {
      java.lang.Object ref = session_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        session_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string session = 1;</code>
     * @param value The session to set.
     * @return This builder for chaining.
     */
    public Builder setSession(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      session_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string session = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSession() {
      
      session_ = getDefaultInstance().getSession();
      onChanged();
      return this;
    }
    /**
     * <code>string session = 1;</code>
     * @param value The bytes for session to set.
     * @return This builder for chaining.
     */
    public Builder setSessionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      session_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object profileId_ = "";
    /**
     * <code>string profileId = 2;</code>
     * @return The profileId.
     */
    public java.lang.String getProfileId() {
      java.lang.Object ref = profileId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        profileId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string profileId = 2;</code>
     * @return The bytes for profileId.
     */
    public com.google.protobuf.ByteString
        getProfileIdBytes() {
      java.lang.Object ref = profileId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        profileId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string profileId = 2;</code>
     * @param value The profileId to set.
     * @return This builder for chaining.
     */
    public Builder setProfileId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      profileId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string profileId = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearProfileId() {
      
      profileId_ = getDefaultInstance().getProfileId();
      onChanged();
      return this;
    }
    /**
     * <code>string profileId = 2;</code>
     * @param value The bytes for profileId to set.
     * @return This builder for chaining.
     */
    public Builder setProfileIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      profileId_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object groupId_ = "";
    /**
     * <code>string groupId = 3;</code>
     * @return The groupId.
     */
    public java.lang.String getGroupId() {
      java.lang.Object ref = groupId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        groupId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string groupId = 3;</code>
     * @return The bytes for groupId.
     */
    public com.google.protobuf.ByteString
        getGroupIdBytes() {
      java.lang.Object ref = groupId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        groupId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string groupId = 3;</code>
     * @param value The groupId to set.
     * @return This builder for chaining.
     */
    public Builder setGroupId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      groupId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string groupId = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearGroupId() {
      
      groupId_ = getDefaultInstance().getGroupId();
      onChanged();
      return this;
    }
    /**
     * <code>string groupId = 3;</code>
     * @param value The bytes for groupId to set.
     * @return This builder for chaining.
     */
    public Builder setGroupIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      groupId_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.example.RemoveGroupFromProfileMessage)
  }

  // @@protoc_insertion_point(class_scope:com.example.RemoveGroupFromProfileMessage)
  private static final com.examples.lib.RemoveGroupFromProfileMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.examples.lib.RemoveGroupFromProfileMessage();
  }

  public static com.examples.lib.RemoveGroupFromProfileMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RemoveGroupFromProfileMessage>
      PARSER = new com.google.protobuf.AbstractParser<RemoveGroupFromProfileMessage>() {
    @java.lang.Override
    public RemoveGroupFromProfileMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new RemoveGroupFromProfileMessage(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RemoveGroupFromProfileMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RemoveGroupFromProfileMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.examples.lib.RemoveGroupFromProfileMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
