//theme styling here
//키에 해당하는 값이 해당 DOM 노드의 클래스로 추가된다.
//직접 CSS를 쓰거나, 유틸리티 클래스를 쓰면 된다.

export const myTheme = {
  // Text alignment
  ltr: 'text-left',
  rtl: 'text-right',
  paragraph: 'mb-4 text-gray-800 leading-normal',

  // Headings
  heading: {
    h1: 'text-4xl font-bold mb-6 text-gray-900',
    h2: 'text-3xl font-semibold mb-5 text-gray-800',
    h3: 'text-2xl font-medium mb-4 text-gray-700',
    h4: 'text-xl font-medium mb-3 text-gray-700',
    h5: 'text-lg font-medium mb-2 text-gray-700',
    h6: 'text-base font-medium mb-2 text-gray-700',
  },

  // Lists
  list: {
    ul: 'list-disc list-inside mb-4 pl-5',
    ol: 'list-decimal list-inside mb-4 pl-5',
    listitem: 'mb-1 text-gray-800',
    nested: {
      listitem: 'mb-1 text-gray-700',
    },
    checklist: 'list-none pl-0',
  },

  // Links
  link: 'text-blue-600 hover:text-blue-800 underline',

  // Quote
  quote: 'border-l-4 border-gray-300 pl-4 py-2 mb-4 italic text-gray-600',

  // Code
  code: 'font-mono bg-gray-100 rounded px-1 py-0.5 text-sm',
  codeHighlight: {
    atrule: 'text-blue-600',
    attr: 'text-purple-600',
    boolean: 'text-red-600',
    builtin: 'text-yellow-600',
    cdata: 'text-gray-600',
    char: 'text-green-600',
    class: 'text-blue-600',
    'class-name': 'text-blue-600',
    comment: 'text-gray-500 italic',
    constant: 'text-purple-600',
    deleted: 'text-red-600',
    doctype: 'text-gray-600',
    entity: 'text-yellow-600',
    function: 'text-green-600',
    important: 'text-red-600 font-bold',
    inserted: 'text-green-600',
    keyword: 'text-purple-600',
    namespace: 'text-yellow-600',
    number: 'text-red-600',
    operator: 'text-yellow-600',
    prolog: 'text-gray-600',
    property: 'text-blue-600',
    punctuation: 'text-gray-700',
    regex: 'text-red-600',
    selector: 'text-purple-600',
    string: 'text-green-600',
    symbol: 'text-purple-600',
    tag: 'text-red-600',
    url: 'text-blue-600 underline',
    variable: 'text-yellow-600',
  },

  // Tables
  table: 'w-full border-collapse mb-4',
  tableCell: 'border border-gray-300 p-2',
  tableRow: 'bg-white even:bg-gray-50',
  tableCellHeader: 'bg-gray-100 font-semibold',

  // Text formatting
  bold: 'font-bold',
  italic: 'italic',
  underline: 'underline',
  strikethrough: 'line-through',
  subscript: 'align-sub text-xs',
  superscript: 'align-super text-xs',
  underlineStrikethrough: 'underline line-through',

  // Images
  image: 'max-w-full h-auto rounded',

  // Horizontal rule
  hr: 'border-t border-gray-300 my-6',

  // Keyboard shortcut
  kbd: 'bg-gray-100 border border-gray-300 rounded px-1 py-0.5 text-xs font-mono',

  // Inline code
  inlineCode: 'font-mono bg-gray-100 rounded px-1 py-0.5 text-sm',

  // Text colors
  textColor: {
    default: 'text-gray-800',
    muted: 'text-gray-600',
    success: 'text-green-600',
    error: 'text-red-600',
    warning: 'text-yellow-600',
  },

  // Background colors
  backgroundColor: {
    default: 'bg-white',
    muted: 'bg-gray-100',
    success: 'bg-green-100',
    error: 'bg-red-100',
    warning: 'bg-yellow-100',
  },

  // Editor specific
  editor: 'prose max-w-none text-gray-800',
  editorFocus: 'outline-none',
};