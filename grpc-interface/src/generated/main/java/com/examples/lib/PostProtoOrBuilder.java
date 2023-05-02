// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PostService.proto

package com.examples.lib;

public interface PostProtoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.example.PostProto)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string postId = 1;</code>
   * @return The postId.
   */
  java.lang.String getPostId();
  /**
   * <code>string postId = 1;</code>
   * @return The bytes for postId.
   */
  com.google.protobuf.ByteString
      getPostIdBytes();

  /**
   * <code>string author = 2;</code>
   * @return The author.
   */
  java.lang.String getAuthor();
  /**
   * <code>string author = 2;</code>
   * @return The bytes for author.
   */
  com.google.protobuf.ByteString
      getAuthorBytes();

  /**
   * <code>string text = 3;</code>
   * @return The text.
   */
  java.lang.String getText();
  /**
   * <code>string text = 3;</code>
   * @return The bytes for text.
   */
  com.google.protobuf.ByteString
      getTextBytes();

  /**
   * <code>repeated .com.example.AttachmentProto attachments = 4;</code>
   */
  java.util.List<com.examples.lib.AttachmentProto> 
      getAttachmentsList();
  /**
   * <code>repeated .com.example.AttachmentProto attachments = 4;</code>
   */
  com.examples.lib.AttachmentProto getAttachments(int index);
  /**
   * <code>repeated .com.example.AttachmentProto attachments = 4;</code>
   */
  int getAttachmentsCount();
  /**
   * <code>repeated .com.example.AttachmentProto attachments = 4;</code>
   */
  java.util.List<? extends com.examples.lib.AttachmentProtoOrBuilder> 
      getAttachmentsOrBuilderList();
  /**
   * <code>repeated .com.example.AttachmentProto attachments = 4;</code>
   */
  com.examples.lib.AttachmentProtoOrBuilder getAttachmentsOrBuilder(
      int index);

  /**
   * <code>string sentTime = 5;</code>
   * @return The sentTime.
   */
  java.lang.String getSentTime();
  /**
   * <code>string sentTime = 5;</code>
   * @return The bytes for sentTime.
   */
  com.google.protobuf.ByteString
      getSentTimeBytes();

  /**
   * <code>repeated .com.example.CommentProto comments = 6;</code>
   */
  java.util.List<com.examples.lib.CommentProto> 
      getCommentsList();
  /**
   * <code>repeated .com.example.CommentProto comments = 6;</code>
   */
  com.examples.lib.CommentProto getComments(int index);
  /**
   * <code>repeated .com.example.CommentProto comments = 6;</code>
   */
  int getCommentsCount();
  /**
   * <code>repeated .com.example.CommentProto comments = 6;</code>
   */
  java.util.List<? extends com.examples.lib.CommentProtoOrBuilder> 
      getCommentsOrBuilderList();
  /**
   * <code>repeated .com.example.CommentProto comments = 6;</code>
   */
  com.examples.lib.CommentProtoOrBuilder getCommentsOrBuilder(
      int index);
}