'use client'

import {LexicalComposer} from "@lexical/react/LexicalComposer";
import {RichTextPlugin} from "@lexical/react/LexicalRichTextPlugin";
import {ContentEditable} from "@lexical/react/LexicalContentEditable";
import {LexicalErrorBoundary} from "@lexical/react/LexicalErrorBoundary";
import {HistoryPlugin} from "@lexical/react/LexicalHistoryPlugin";
import {AutoFocusPlugin} from "@lexical/react/LexicalAutoFocusPlugin";
import {myTheme} from '@/config/lexical/theme'
import {useLexicalComposerContext} from "@lexical/react/LexicalComposerContext";
import {useEffect, useState} from "react";
import {onChange} from "lib0/storage";

const theme = myTheme

function onError() {
  console.error('error occured')
}

function MyOnChangePlugin({onChange}) {
  const [editor] = useLexicalComposerContext();
  useEffect(() => {
    return editor.registerUpdateListener(({editorState}) => {
      console.log(editorState.toJSON());
      onChange(editorState);
    });
  }, [editor, onChange]);
  return null;
}


export default function BasicEditor() {
  const [editorState, setEditorState] = useState();
  function onChange(editorState) {
    setEditorState(editorState);
  }

  const initialConfig = {
    namespace: 'MyEditor',
    myTheme,
    onError,
  };

  return (
      <LexicalComposer initialConfig={initialConfig}>
        <RichTextPlugin
            contentEditable={<ContentEditable/>}
            placeholder={<div>EDITABLE DIV IS HERE</div>}
            ErrorBoundary={LexicalErrorBoundary}
        />

        <HistoryPlugin/>
        <AutoFocusPlugin/>
        <MyOnChangePlugin onChange={onChange}/>

      </LexicalComposer>
  )
}